package com.mobileai.luncert.utils;

import com.mobileai.luncert.utils.Neo4jUtil;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;

@RunWith(JUnit4.class)
public class TestNeo4jUtil {
    private static Logger logger = Logger.getLogger(TestNeo4jUtil.class);
    
    @Test
    public void test() {
        Session session = Neo4jUtil.open();
        
        StatementResult result = session.run("match (c:Scene) return c limit 25");
        while (result.hasNext()) {
            Record record = result.next();
            Value value = record.get("c");
            logger.info(value.get("name", "") + ":" + value.get("eventId", -1));
        }

        Neo4jUtil.close(session, false);
    }
}