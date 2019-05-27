package com.mobileai.luncert.utils.mulsession;

import java.util.Map;

/**
 * manage session
 */
public class MulSession {

    private Map<String, Object> domain;

    public MulSession(Map<String, Object> domain) {
        this.domain = domain;
    }

    public void setPair(String key, Object value) { domain.put(key, value); }

    @SuppressWarnings("unchecked")
    public <T> T getValue(Object key, Class<T> type) {
        return (T)domain.get(key);
    }

}