package com.mobileai.luncert.core.storm.bolt;

import java.util.Map;

import com.mobileai.luncert.core.KnowledgeGraph;
import com.mobileai.luncert.model.core.MatchResult;
import com.mobileai.luncert.model.core.SecurityEvent;
import com.mobileai.luncert.model.neo4j.node.Event;
import com.mobileai.luncert.utils.Graph.Path;
import com.mobileai.luncert.utils.Graph.TreeNode;
import com.mobileai.luncert.utils.mullog.Mullog;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class VirusMatch extends BaseRichBolt {

    private static final long serialVersionUID = 9041216431493836578L;
    
    private OutputCollector collector;

    private KnowledgeGraph graph;
    
    private Path<TreeNode<Event>> records;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        graph = KnowledgeGraph.getInstance();
        records = new Path<>();
	}

	@Override
	public void execute(Tuple input) {
        
        SecurityEvent event = (SecurityEvent)input.getValueByField("event");
        
        Mullog.info("virusMatch: event " + event.getId());
        
        Values toemit;

        if (!records.beEmpty() && records.lastNode().getParent().match(event.getId())) {
            TreeNode<Event> parent = records.lastNode().getParent(); // 获取上次匹配节点的下一个节点
            if (parent.getValue().beScene()) { // 判断节点是否是Scene
                // 匹配结束，开始新的匹配
                records.clear();
                toemit = new Values(MatchResult.MATCH_SCENE_SUCCESS, event);
            }
            else {
                // 添加到匹配记录
                records.addNode(parent);
                toemit = new Values(MatchResult.MATCH_SUCCESS, event);
            }
        }
        else {
            // 开始新的匹配
            TreeNode<Event> node = graph.getRoot().find(event.getId());

            if (node == null) {
                throw new RuntimeException("no node found for id: " + event.getId() + " -> " + graph.getLeafNodes().size());
            }

            if (node.getValue().beScene()) {
                records.clear();
                toemit = new Values(MatchResult.MATCH_SCENE_FAILED, event);
            }
            else {
                if (records.beEmpty()) toemit = new Values(MatchResult.MATCH_SUCCESS, event);
                else {
                    records.clear();
                    toemit = new Values(MatchResult.MATCH_FAILED, event);
                }
                records.addNode(node);
            }
        }
        
        collector.emit(toemit);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("matchResult", "event"));
    }

}