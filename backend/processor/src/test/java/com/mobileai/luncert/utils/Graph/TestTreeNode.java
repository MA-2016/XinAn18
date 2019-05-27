package com.mobileai.luncert.utils.Graph;

import com.mobileai.luncert.utils.Graph.TreeNode;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestTreeNode {
    private static Logger logger = Logger.getLogger(TestTreeNode.class);

    private void outputTree(TreeNode<String> node, int level) {
        for (int i = 0; i < level; i++) System.out.print(" ");
        System.out.println("-> " + node.getValue());
        for (TreeNode<String> child : node.getChildren()) outputTree(child, level + 1);
    }

    @Test
    public void test() {
        TreeNode<String> root = new TreeNode<>(1, "root");
        TreeNode<String> node1 = new TreeNode<>(2, "node-id-2", root);
        TreeNode<String> node2 = new TreeNode<>(3, "node-id-3", root);
        node1.addChild(4, "node-id-4");
        node2.addChild(4, "node-id-4");

        outputTree(root, 0);
        
        System.out.println(root.match(1));

        for (TreeNode<String> node : root.matchAll(4)) {
            logger.info(node.getId() + ":" + node.getValue() + ":" + ((TreeNode<String>)node).getParent().getValue());
        }

        System.out.println(root.find(3));
    }
}