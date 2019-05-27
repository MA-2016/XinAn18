package com.mobileai.luncert.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mobileai.luncert.utils.CipherHelper.SM2Utils;
import com.mobileai.luncert.utils.CipherHelper.SM4Utils;

import org.bouncycastle.util.encoders.Base64;

import net.sf.json.JSONObject;

public class SecurityTransport {

    private String sm2Rpuk;

    private String sm2Lprk;

    private String sm4Key;

    private SM4Utils sm4 = new SM4Utils();

    /**
     * @param sm2Rpuk: 客户端提交的公钥
     * @param sm2Lprk: 服务器端私钥
     */
    public SecurityTransport(String sm2Rpuk, String sm2Lprk, String sm4Key) {
        this.sm2Rpuk = sm2Rpuk;
        this.sm2Lprk = sm2Lprk;
        this.sm4Key = sm4Key;
        sm4.setSecretKey(sm4Key);
        sm4.setHexString(false);
    }
    
    /**
     * field split with &
     */
    public Map<String, String> load(String cipherText) throws IOException {
        String plainText = new String(SM2Utils.decrypt(Base64.decode(sm2Rpuk), cipherText.getBytes()));
        Map<String, String> ret = new HashMap<>();
        for (String field : plainText.split("&")) {
            String[] tmp = field.split("=");
            ret.put(tmp[0], tmp[1]); 
        }
        return ret;
    }

    /**
     * 将data通过本地sm2私钥加密后传输
     */
    public String parseSM2(String data) throws IOException {
        byte[] cipherText = SM2Utils.encrypt(Base64.decode(sm2Lprk.getBytes()), data.getBytes());
        return new String(Base64.encode(cipherText));
    }

    public String parseSM4(String data) { return sm4.encryptData_ECB(data); }

    public String response(int code, String desc, Object data) throws IOException {
        JSONObject json = new JSONObject();
        json.put("code", code);
        if (desc != null) json.put("desc", desc);
        if (data != null) json.put("data", data);
        return parseSM4(json.toString());
    }

    // getter

    public String getSM4Key() { return sm4Key; }

}