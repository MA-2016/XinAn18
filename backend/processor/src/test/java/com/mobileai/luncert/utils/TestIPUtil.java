package com.mobileai.luncert.utils;

import java.io.IOException;
import java.util.Arrays;

import com.mobileai.luncert.utils.IPUtil;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestIPUtil {

    private static Logger logger = Logger.getLogger(TestIPUtil.class);
    
    @Test
    public void testCast() {
        String ipStr = "127.0.0.1";
        int ip = IPUtil.ipToInt(ipStr);

        logger.info(ip);
        logger.info(IPUtil.ipToString(ip));

        logger.info(Arrays.toString(IPUtil.ipToByte(ipStr)));
    }

    @Test
    public void testQueryPos() throws IOException {
        System.out.println(IPUtil.queryPos("47.254.30.49"));
    }

}