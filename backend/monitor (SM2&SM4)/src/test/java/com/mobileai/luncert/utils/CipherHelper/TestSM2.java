package com.mobileai.luncert.utils.CipherHelper;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import com.mobileai.luncert.utils.CipherHelper.SM2Utils;
import com.mobileai.luncert.utils.CipherHelper.Util;


@RunWith(JUnit4.class)
public class TestSM2 {

    private static Logger logger = Logger.getLogger(TestSM2.class);

    @Test
    public void testSM2() throws Exception {
        String plainText = "message digest";
        byte[] sourceData = plainText.getBytes();

        // 国密规范测试私钥
        String prik = "128B2FA8BD433C6C068C8D803DFF79792A519A55171B1B650C23661D15897263";
        //String prik = "ncsancjnankqwnio1o3en1j23nj12n3enj12nej21bje2n13j12nj1bw12njn12j";
        String prikS = new String(Base64.encode(Util.hexToByte(prik)));
        logger.info("prikS: " + prikS);

        // 国密规范测试公钥
        String pubk = "040AE4C7798AA0F119471BEE11825BE46202BB79E2A5844495E97C04FF4DF2548A7C0240F88F1CD4E16352A73C17B7F16F07353E53A176D684A9FE0C6BB798E857";
        String pubkS = new String(Base64.encode(Util.hexToByte(pubk)));
        logger.info("pubkS: " + pubkS);

        // 国密规范测试用户ID
        String userId = "ALICE123@YAHOO.COM";
        logger.info("ID: " + Util.getHexString(userId.getBytes()));

        byte[] c = SM2Utils.sign(userId.getBytes(), Base64.decode(prikS.getBytes()), sourceData);
        logger.info("sign: " + Util.getHexString(c));

        boolean vs = SM2Utils.verifySign(userId.getBytes(), Base64.decode(pubkS.getBytes()), sourceData, c);
        logger.info("验签结果: " + vs);

        byte[] cipherText = SM2Utils.encrypt(Base64.decode(pubkS.getBytes()), sourceData);
        logger.info("加密: " + new String(Base64.encode(cipherText)));

        plainText = new String(SM2Utils.decrypt(Base64.decode(prikS.getBytes()), cipherText));
        logger.info("解密: " + plainText);
    }




}
