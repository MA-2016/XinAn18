package com.mobileai.luncert;

import java.util.ArrayList;
import java.util.List;

import com.mobileai.luncert.core.storm.bolt.EventFilter;
import com.mobileai.luncert.core.storm.bolt.EventRecord;
import com.mobileai.luncert.core.storm.bolt.VirusDetect;
import com.mobileai.luncert.core.storm.bolt.VirusMatch;
import com.mobileai.luncert.core.storm.spout.LocalSpout;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class Application {

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		String topic = "flumeDemo";
		ZkHosts zkHosts = new ZkHosts("localhost:2181");
		// (hosts, topic, zkRoot, id);
		SpoutConfig spoutConfig = new SpoutConfig(zkHosts, topic, "", "AttackEvent");
		List<String> zkServers =  new ArrayList<>();
		zkServers.add("worker100"); 
		zkServers.add("worker101"); 
		zkServers.add("worker102");
		spoutConfig.zkServers = zkServers;
		spoutConfig.zkPort = 2181;
        spoutConfig.socketTimeoutMs = 60 * 1000 ;
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		
		TopologyBuilder builder = new TopologyBuilder();
		// builder.setSpout("kafkaSpout", new KafkaSpout(spoutConfig), 3).setNumTasks(8);
		builder.setSpout("spout", new LocalSpout(), 1);
		
        // level 1
        builder.setBolt("eventFilter", new EventFilter(), 1).setNumTasks(1).shuffleGrouping("spout");

        // level 2
        builder.setBolt("virusMatch", new VirusMatch(), 2).setNumTasks(4).fieldsGrouping("eventFilter", new Fields("event"));
       
        // level 3
        builder.setBolt("virusDetect", new VirusDetect(), 2).setNumTasks(4).fieldsGrouping("virusMatch", new Fields("matchResult", "event"));
        // builder.setBolt("hostRecord", new HostRecord(), 2).setNumTasks(4).fieldsGrouping("virusMatch", new Fields("matchResult", "event"));
		builder.setBolt("eventRecord", new EventRecord(), 2).setNumTasks(4).fieldsGrouping("virusMatch", new Fields("matchResult", "event"));
		
        Config conf = new Config();
        conf.setNumWorkers(4);
        conf.setDebug(true);
		conf.setNumAckers(0);
		// conf.registerSerialization(SecurityEvent.class);

        StormSubmitter.submitTopology("AttackStormTopology", conf, builder.createTopology());
	}
}