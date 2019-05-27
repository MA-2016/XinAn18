package com.mobileai.luncert.utils.mulsession;

import java.util.HashMap;
import java.util.Map;

import com.mobileai.luncert.utils.CipherHelper;

public class MulSessionManager {
    
    private static Map<String, Map<String, Object>> domains = new HashMap<>();

    public static MulSession getSession(String sessionId) {
        Map<String, Object> domain = domains.get(sessionId);
        if (domain != null) return new MulSession(domain);
        else return null;
    }

    public static String createSession() {
        String sessionId = CipherHelper.getUUID(32);
        domains.put(sessionId, new HashMap<>());
        return sessionId;
    }

    public static boolean hasSession(String sessionId) { return domains.containsKey(sessionId); }
}