package com.mobileai.luncert.core.storm.bolt;

import java.util.Map;

import com.mobileai.luncert.model.core.SecurityEvent;
import com.mobileai.luncert.model.mysql.PHostMapper;
import com.mobileai.luncert.model.mysql.entity.PHost;
import com.mobileai.luncert.utils.MySQLUtil;

import org.apache.ibatis.session.SqlSession;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class EventFilter extends BaseRichBolt {

    private static final long serialVersionUID = 1L;

	private OutputCollector collector;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		SecurityEvent event = (SecurityEvent)input.getValue(0);
		
		SqlSession session = MySQLUtil.open();
		
		PHost phost = session.getMapper(PHostMapper.class).findByIp(event.getTarget());
		if (phost == null) collector.emit(new Values(event));

		MySQLUtil.close(session);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("event"));
	}

}