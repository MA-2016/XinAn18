package com.mobileai.luncert.utils.CipherHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.UUID;

@RunWith(JUnit4.class)
public class TestGetUUID {
    
    @Test
    public void test(){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        System.out.println(uuid);
    }
}
