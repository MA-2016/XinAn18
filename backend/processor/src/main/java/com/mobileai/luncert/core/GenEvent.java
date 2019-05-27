package com.mobileai.luncert.core;

import java.util.List;
import java.util.Random;

import com.mobileai.luncert.model.core.SecurityEvent;
import com.mobileai.luncert.model.neo4j.node.Event;
import com.mobileai.luncert.utils.Graph.Path;
import com.mobileai.luncert.utils.Graph.TreeNode;
import com.mobileai.luncert.utils.mullog.Mullog;


public class GenEvent {
    
    private KnowledgeGraph graph = KnowledgeGraph.getInstance();

    private int lastTarget;

    public Path<TreeNode<Event>> path = new Path<>();

    public SecurityEvent nextEvent() {
        Random random = new Random();

        TreeNode<Event> node;
        if (path.size() > 0 && random.nextInt(100) > 20 && !path.lastNode().getParent().beRoot) {
            node = path.lastNode().getParent();
        }
        else {
            List<TreeNode<Event>> leafNodes = graph.getLeafNodes();
            node = leafNodes.get(random.nextInt(leafNodes.size()));
            path.clear(); // start a new path
            lastTarget = genIp();
        }

        Mullog.info("event: " + node.getId());

        path.addNode(node);
        return new SecurityEvent(
            node.getId(),
            genIp(),
            lastTarget,
            node.getValue().getName(),
            node.getValue().getEventId(),
            node.getValue().getType());
    }

	private int genIp() {
        final Random random = new Random();

        int ret = 0;
        for (int i = 0; i < 4; i++) ret += (random.nextInt(254) + 1) << (8 * i);
		return ret;
    }
    
}