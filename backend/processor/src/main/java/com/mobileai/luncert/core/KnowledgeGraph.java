package com.mobileai.luncert.core;

import java.util.ArrayList;
import java.util.List;

import com.mobileai.luncert.model.neo4j.node.Event;
import com.mobileai.luncert.utils.Neo4jUtil;
import com.mobileai.luncert.utils.Graph.TreeNode;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

/**
 * 单例
 */
public class KnowledgeGraph{

    private TreeNode<Event> root = null;

    private List<TreeNode<Event>> leafNodes;

    public static KnowledgeGraph getInstance() { return MatchGraphInner.INSTANCE; }

    public TreeNode<Event> getRoot() { return root; }

    public List<TreeNode<Event>> getLeafNodes() { return leafNodes; }

    public void learn(List<com.mobileai.luncert.model.mysql.entity.Event> records) {
        // learn
        
        updateGraph();
    }

    private void updateGraph() {
        // add new path to memory graph and neo4j
        
        /*
        if (bestMatch != null && bestMatchCompactSum > THRESHOLD) {
            System.out.println(bestMatch.getSeq());
            System.out.println(bestMatch.getCompactSum());
            Session session = Neo4jUtil.open();
            StringBuilder statement;
            int[] bestSeq = bestMatch.getSeq();
            for (int i = 1, limit = MAX_SEQ_LENGTH; i < limit; i++) {
                statement = new StringBuilder().append("MATCH (e1:Event)-[r:Derivation]->(e2:Event) WHERE e1.id = ").append(bestSeq[i - 1]).append(" AND e2.id = ").append(bestSeq[i]).append(" RETURN r");
                StatementResult result = session.run(statement.toString());
                if (!result.hasNext()) {
                    // 该边不存在
                    statement = new StringBuilder().append("CREATE (e1:Event)-[:Derivation]->(e2:Event) WHERE e1.id = ").append(bestSeq[i - 1]).append(" AND e2.id = ").append(bestSeq[i]);
                    session.run(statement.toString());
                }
            }
            return true;
        } else return false;
        */
    }

    private KnowledgeGraph() { init(); }

    private void init() {
        // create root node
        root = new TreeNode<>();
        leafNodes = new ArrayList<>();

        Session session = Neo4jUtil.open();

        String statement = "MATCH (parent)<-[:Derivation]-(child) RETURN DISTINCT parent, child, ID(parent) as pid, ID(child) as cid";
        StatementResult result = session.run(statement);
        while (result.hasNext()) {
            Record record = result.next();
            Event parentEntity = new Event(record.get("parent"));
            Event childEntity = new Event(record.get("child"));
            int pid = record.get("pid", -1);
            int cid = record.get("cid", -1);
            List<TreeNode<Event>> matchRet = root.matchAll(pid);
            if (matchRet.size() > 0) {
                // create new instance for childEntity, add to each matched parent node
                for (TreeNode<Event> node : matchRet) {
                    if (!((TreeNode<Event>)node).hasChild(cid)) {
                        new TreeNode<>(cid, childEntity, node);
                    }
                }
            } else {
                TreeNode<Event> parent = new TreeNode<>(pid, parentEntity, root);
                new TreeNode<>(cid, childEntity, parent);
            }
        }

        leafNodes = root.getLeafNodes();

        Neo4jUtil.close(session, true);
    }

    private static class MatchGraphInner {
        private static final KnowledgeGraph INSTANCE = new KnowledgeGraph();
    }

}