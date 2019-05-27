package com.mobileai.luncert.utils;

import java.util.ArrayList;
import java.util.List;

import com.mobileai.luncert.utils.Redis;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import redis.clients.jedis.JedisPubSub;

@RunWith(JUnit4.class)
public class TestRedis {

    private static Logger logger = Logger.getLogger(TestRedis.class);

    private Redis redis = new Redis();

    @Test
    public void testSetPair() {
        redis.setPair("testKey", "testValue");
        
        logger.info(redis.getValue("testKey"));
        
        redis.close();
    }

    @Test
    public void testSetList() {
        List<String> values = new ArrayList<>();
        values.add("HI");
        values.add("Lucy");
        redis.setList("testListKey", values);

        for (String value : redis.getList("testListKey", 0, 2)) {
            logger.info(value);
        }

        redis.close();
    }
    
    @Test
    public void testQueryKeys() {
        for (String key : redis.queryKeys()) {
            logger.info(key);
        }

        redis.close();
    }


    private class CusPubSub extends JedisPubSub {

        @Override
        public void onMessage(String channel, String message) {
            logger.info("Redis >> channel:" + channel + "\n\tmessage: " + message);
        }

    }

    @Test
    public void testPubSub() {
        // redis.subscribe(new CusPubSub(), "redisChat");
        redis.close();
    }

}