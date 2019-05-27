package com.mobileai.luncert.utils.CipherHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestCipher {

    @Test
    public void test() {
        System.out.println(Cipher.getUUID(32));    
    }
    
}