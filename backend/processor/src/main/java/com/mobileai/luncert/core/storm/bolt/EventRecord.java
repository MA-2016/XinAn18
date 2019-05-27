package com.mobileai.luncert.core.storm.bolt;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.mobileai.luncert.core.KnowledgeGraph;
import com.mobileai.luncert.model.core.MatchResult;
import com.mobileai.luncert.model.core.SecurityEvent;
import com.mobileai.luncert.model.mysql.EventMapper;
import com.mobileai.luncert.model.mysql.entity.Event;
import com.mobileai.luncert.utils.Convert;
import com.mobileai.luncert.utils.IPUtil;
import com.mobileai.luncert.utils.MySQLUtil;
import com.mobileai.luncert.utils.Redis;
import com.mobileai.luncert.utils.Graph.TreeNode;
import com.mobileai.luncert.utils.mullog.Mullog;

import org.apache.ibatis.session.SqlSession;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class EventRecord extends BaseRichBolt {

	private static final long serialVersionUID = -6772337251072774043L;

	private Redis redis;

    private KnowledgeGraph graph;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		redis  = new Redis();
        graph = KnowledgeGraph.getInstance();
	}

	@Override
	public void execute(Tuple input) {
		int matchResult = (int)input.getValue(0);
		SecurityEvent securityEvent = (SecurityEvent)input.getValue(1);

		// query ip pos
		String srcPos = null;
		String tarPos = null;
		try {
			srcPos = IPUtil.queryPos(securityEvent.getSource()).toJSONString();
			tarPos = IPUtil.queryPos(securityEvent.getSource()).toJSONString();
		} catch (IOException e) {
			Mullog.error(e.toString());
			throw new RuntimeException(e);
		}

		boolean beScene = (matchResult == MatchResult.MATCH_SCENE_FAILED || matchResult == MatchResult.MATCH_SCENE_SUCCESS);

		TreeNode<com.mobileai.luncert.model.neo4j.node.Event> node = graph.getRoot().find(securityEvent.getId());
		while (!node.getValue().beScene())
			node = node.getParent();
		String rootEventName = node.getValue().getName();

		Event event = new Event(
			new Date(),
			securityEvent.getSource(),
			securityEvent.getTarget(),
			srcPos,
			tarPos,
			securityEvent.getName(),
			securityEvent.getEventId(),
			securityEvent.getType(),
			beScene,
			(matchResult == MatchResult.MATCH_SUCCESS || matchResult == MatchResult.MATCH_SCENE_SUCCESS),
			rootEventName
			);

		// add event to mysql
		SqlSession session = MySQLUtil.open();
		// save Event
		session.getMapper(EventMapper.class).addEvent(
			Convert.dateCastString(event.getTime()),
			event.getSource(),
			event.getTarget(),
			srcPos,
			tarPos,
			event.getName(),
			event.getEventId(),
			event.getEventType(),
			event.getBeScene(),
			event.getAttackSuccess(),
			event.getAttackType());
		MySQLUtil.close(session);

		// add Event to Redis
		redis.publish("SecurityAnalyse", event.toJSONString());
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("eventRecord"));
	}

}