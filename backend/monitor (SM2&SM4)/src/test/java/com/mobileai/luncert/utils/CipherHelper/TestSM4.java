package com.mobileai.luncert.utils.CipherHelper;


import java.io.IOException;

import com.mobileai.luncert.utils.CipherHelper.SM4Utils;

import org.junit.Test;

public class TestSM4 {

    @Test
    public void testSM4() throws IOException {
        String plainText = "ajscnascnoancasncjnjwqnacwdcjscnajbcjnbasjkdbjkasjckbiajsbcjabwbwbajcjandawcnqk.;as;cml211e";

        SM4Utils sm4 = new SM4Utils();
        sm4.setSecretKey("JeF8UygsiOMfs2Y8");
        sm4.setHexString(false);

        //ECB利于并行计算
        System.out.println("ECB模式");
        String cipherText = sm4.encryptData_ECB(plainText);
        System.out.println("密文: " + cipherText);
        System.out.println("");

        plainText = sm4.decryptData_ECB(cipherText);
        System.out.println("明文: " + plainText);
        System.out.println("");

        System.out.println("CBC模式");
        sm4.setIv("UISwD9fW6cFh9SNS");
        cipherText = sm4.encryptData_CBC(plainText);
        System.out.println("密文: " + cipherText);
        System.out.println("");

        plainText = sm4.decryptData_CBC(cipherText);
        System.out.println("明文: " + plainText);
    }
}
