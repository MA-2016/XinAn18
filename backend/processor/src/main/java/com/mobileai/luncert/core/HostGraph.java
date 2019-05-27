package com.mobileai.luncert.core;

import java.util.ArrayList;
import java.util.List;

import com.mobileai.luncert.model.core.SecurityEvent;
import com.mobileai.luncert.model.neo4j.node.Host;
import com.mobileai.luncert.model.neo4j.relationship.Reference;
import com.mobileai.luncert.utils.Neo4jUtil;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;

public class HostGraph {

    private Driver driver;

    public static HostGraph getInstance() { return HostGraphInner.INSTANCE; }

    private Host queryHost(Session session, int ip) {
        Host source;
        StringBuilder statement = new StringBuilder().append("MATCH (host:Host) WHERE host.ip = ").append(ip).append(" RETURN host");
        StatementResult result = session.run(statement.toString());
        if (result.hasNext()) source = new Host(result.next().get("host"));
        else {
            // create a new host
            statement = new StringBuilder().append("CREATE (host:Host{ip: ").append(ip).append(", riskEstimates: 0}) RETURN host");
            source = new Host(session.run(statement.toString()).next().get("host"));
        }
        return source;
    }

    public void update(SecurityEvent event) {
        Session session = driver.session();

        Host source;

        // if source doesn't exist, create  a new host
        source = queryHost(session, event.getSource());

        // if target doesn't exist, create  a new host
        queryHost(session, event.getTarget());

        StringBuilder statement = new StringBuilder()
                .append("MATCH (source:Host)-[ref:Reference]->(target:Host) WHERE target.ip = ")
                .append(event.getTarget())
                .append(" AND source.ip = ")
                .append(event.getSource())
                .append(" RETURN ref");
        StatementResult result = session.run(statement.toString());
        if (result.hasNext()) {
            Reference ref = new Reference(result.next().get("ref"));
            ref.setProbability(event.getTypeIdentifier());
            ref.calcRiskEstimates();
            // 更新source对target的访问记录
            statement = new StringBuilder()
                        .append("MATCH (source:Host)-[ref:Reference]->(target:Host) WHERE source.ip = ").append(event.getSource())
                        .append(" AND target.ip = ").append(event.getTarget())
                        .append(" SET ref.riskEstimates = ").append(ref.getRiskEstimates())
                        .append(", ref.l1Probability = ").append(ref.getL1Probability())
                        .append(", ref.l2Probability = ").append(ref.getL2Probability())
                        .append(", ref.l3Probability = ").append(ref.getL3Probability())
                        .append(", ref.l4Probability = ").append(ref.getL4Probability());
            session.run(statement.toString());
        } else {
            // source主机对target主机无访问记录，创建关系
            Reference ref = new Reference();
            ref.setProbability(event.getTypeIdentifier());
            statement = new StringBuilder()
                .append("MATCH (source:Host), (target:Host) WHERE source.ip = ").append(event.getSource())
                .append(" AND target.ip = ").append(event.getTarget())
                .append(" CREATE (source)-[r:Reference{riskEstimates: ").append(ref.getRiskEstimates())
                .append(", l1Probability: ").append(ref.getL1Probability())
                .append(", l2Probability: ").append(ref.getL2Probability())
                .append(", l3Probability: ").append(ref.getL3Probability())
                .append(", l4Probability: ").append(ref.getL4Probability())
                .append("}]->(target)");
            session.run(statement.toString());
        }


        // 获取所有source主机对其他主机的访问记录，计算source主机危险度(期望)
        Double totalEstimates = 0d, riskEstimatesExp = 0d;
        List<Double> refEstimates = new ArrayList<>();
        statement = new StringBuilder()
            .append("MATCH (source:Host)-[ref:Reference]->() WHERE source.ip = ")
            .append(event.getSource())
            .append(" RETURN ref");
        result = session.run(statement.toString());
        while (result.hasNext()) {
            Value ref = result.next().get("ref");
            refEstimates.add(ref.get("riskEstimates", 0d));
            totalEstimates += refEstimates.get(refEstimates.size() - 1);
        }
        for (int i = 0, limit = refEstimates.size(); i < limit; i++) {
            riskEstimatesExp += refEstimates.get(i) * refEstimates.get(i) / totalEstimates;
        }
        source.setRiskEstimates(riskEstimatesExp);

        // 更新主机危险度
        statement = new StringBuilder()
            .append("MATCH (host:Host) WHERE host.ip = ").append(source.getIp())
            .append(" SET host.riskEstimates = ").append(source.getRiskEstimates());
        session.run(statement.toString());

        Neo4jUtil.close(session, false);
    }

    private HostGraph() {
        driver = Neo4jUtil.getDriver();
    }

    private static class HostGraphInner {
        private static final HostGraph INSTANCE = new HostGraph();
    }
}