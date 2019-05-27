package com.mobileai.luncert.utils.CipherHelper;

import com.mobileai.luncert.utils.CipherHelper.SM3Digest;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestSM3 {

    @Test
    public void testSM3() {
        byte[] md = new byte[32];
        byte[] msg1 = "abc".getBytes();
        SM3Digest sm3 = new SM3Digest();
        sm3.update(msg1, 0, msg1.length);
        sm3.doFinal(md, 0);
        String s = new String(Hex.encode(md));
        System.out.println(s);
    }
}
