package com.mobileai.luncert.utils;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestCipherHelper {

    @Test
    public void test() throws NoSuchAlgorithmException {
        System.out.println(CipherHelper.hashcode("admin"));
        System.out.println(CipherHelper.hashcode("123"));
    }

}