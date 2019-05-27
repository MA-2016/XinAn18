package com.mobileai.luncert.utils;

import com.mobileai.luncert.utils.mullog.Mullog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestMullog {

    @Test
    public void test() {
        Mullog.info("hi!");
    }

}