package com.mobileai.luncert.core;

import com.mobileai.luncert.core.HostGraph;
import com.mobileai.luncert.model.core.SecurityEvent;
import com.mobileai.luncert.utils.IPUtil;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestHostGraph {
    private static Logger logger = Logger.getLogger(TestHostGraph.class);

    private HostGraph hostGraph = HostGraph.getInstance();

    @Test
    public void testUpdate() {
        logger.info("TestHostGraph");

        SecurityEvent event = new SecurityEvent(
            1, // node id
            IPUtil.ipToInt("192.168.1.100"),
            IPUtil.ipToInt("192.168.1.105"),
            "xxx",
            1, // event id
            "DetailedAttackPattern");
        hostGraph.update(event);;
    }
}