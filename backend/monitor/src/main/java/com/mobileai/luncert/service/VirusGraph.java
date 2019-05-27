package com.mobileai.luncert.service;

import com.mobileai.luncert.model.neo4j.node.Event;
import com.mobileai.luncert.utils.Neo4jUtil;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.springframework.stereotype.Service;

@Service
public class VirusGraph {

    public String fetchAllDerivation() {
        StringBuilder ret = new StringBuilder();

        Session session = Neo4jUtil.open();
        
        ret.append("[");
        StatementResult result = session.run("MATCH (start:Event)-[:Derivation]->(end:Event) RETURN start, end, ID(start) as sid, ID(end) as eid");
        handle(ret, result);

        result = session.run(("MATCH (start:Event)-[:Derivation]->(end:Scene) RETURN start, end, ID(start) as sid, ID(end) as eid"));
        handle(ret, result);
        
        ret.replace(ret.length() - 1, ret.length(), "]");

        Neo4jUtil.close(session, true);

        return ret.toString();
    }

    private void handle(StringBuilder builder, StatementResult result) {
        while (result.hasNext()) {
            Record record = result.next();
            Event start = new Event(record.get("start"));
            builder.append("{\"start\":{\"eventId\":").append(start.getEventId())
                .append(",\"id\":").append(record.get("sid"))
                .append(",\"eventType\":\"").append(start.getType()).append("\"")
                .append(",\"name\":\"").append(start.getName()).append("\"")
                .append(",\"beScene\":").append(start.getBeScene())
                .append("},");

            Event end = new Event(record.get("end"));
            builder.append("\"end\":{\"eventId\":").append(end.getEventId())
                .append(",\"id\":").append(record.get("eid"))
                .append(",\"eventType\":\"").append(end.getType()).append("\"")
                .append(",\"name\":\"").append(end.getName()).append("\"")
                .append(",\"beScene\":").append(end.getBeScene())
                .append("}},");
        }
    }

}