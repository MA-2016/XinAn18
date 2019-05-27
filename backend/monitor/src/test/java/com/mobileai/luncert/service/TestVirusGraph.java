package com.mobileai.luncert.service;


import com.mobileai.luncert.Application;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestVirusGraph {

    private static Logger logger = Logger.getLogger(TestVirusGraph.class);

    @Autowired
    private VirusGraph virusGraph;

    @Test
    public void testFetchAllDerivationJSON() {
        logger.info(virusGraph.fetchAllDerivation().toString());
    }

}