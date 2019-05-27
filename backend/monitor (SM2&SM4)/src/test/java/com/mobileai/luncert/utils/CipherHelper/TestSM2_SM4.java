package com.mobileai.luncert.utils.CipherHelper;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import com.mobileai.luncert.utils.CipherHelper.SM2Utils;
import com.mobileai.luncert.utils.CipherHelper.SM4Utils;
import com.mobileai.luncert.utils.CipherHelper.Util;

@RunWith(JUnit4.class)
public class TestSM2_SM4 {

    private static Logger logger = Logger.getLogger(TestSM2_SM4.class);

    /**
     * 生成32位编码
     *
     * @return string
     */

    public String SM42SM2(String plainText) throws IOException {
        byte[] sourceData = plainText.getBytes();

        // 国密规范测试私钥
        String prik = "128B2FA8BD433C6C068C8D803DFF79792A519A55171B1B650C23661D15897263";
        String prikS = new String(Base64.encode(Util.hexToByte(prik)));
        System.out.println("prk: " + prikS);
        
        // 国密规范测试公钥
        String pubk = "040AE4C7798AA0F119471BEE11825BE46202BB79E2A5844495E97C04FF4DF2548A7C0240F88F1CD4E16352A73C17B7F16F07353E53A176D684A9FE0C6BB798E857";
        //String pubk = getUUID(32)+getUUID(32)+getUUID(32)+getUUID(32)+getUUID(2);
        String pubkS = new String(Base64.encode(Util.hexToByte(pubk)));
        System.out.println("puk: " + pubkS);

        // 国密规范测试用户ID
        String userId = "ALICE123@YAHOO.COM";
        System.out.println("id: " + Util.getHexString(userId.getBytes()));

        byte[] c = SM2Utils.sign(userId.getBytes(), Base64.decode(prikS.getBytes()), sourceData);
        System.out.println("sign: " + Util.getHexString(c));

        boolean vs = SM2Utils.verifySign(userId.getBytes(), Base64.decode(pubkS.getBytes()), sourceData, c);
        System.out.println("验签结果: " + vs);

        byte[] cipherText = SM2Utils.encrypt(Base64.decode(pubkS.getBytes()), sourceData);
        System.out.println("加密: " + new String(Base64.encode(cipherText)));

        String plainText2 = new String(SM2Utils.decrypt(Base64.decode(prikS.getBytes()), cipherText));
        System.out.println("解密: " + plainText2);

        return plainText2;
    }

    @Test
    public void testSM2_SM4() throws IOException {
        String plainText = "五星红旗迎风飘扬";

        String SM4secretKey = Cipher.getUUID(16);
        logger.info("SM4secretKey: " + SM4secretKey);
        
        SM4Utils sm4 = new SM4Utils();
        sm4.setSecretKey(SM4secretKey);
        sm4.setHexString(false);

        logger.info("ECB模式加密中....");
        String cipherText = sm4.encryptData_ECB(plainText);
        logger.info("密文: " + cipherText);

        logger.info("将SM4密匙用SM2加密中....");
        String secretKey2 = SM42SM2(SM4secretKey);
        logger.info("secretKey =" + SM4secretKey);
        logger.info("secretKey2="+secretKey2);

        sm4.setSecretKey(secretKey2);
        logger.info("ECB模式解密中....");
        plainText = sm4.decryptData_ECB(cipherText);
        logger.info("明文: " + plainText);
    }
}
