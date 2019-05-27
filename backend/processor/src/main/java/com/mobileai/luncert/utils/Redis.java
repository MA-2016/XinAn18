package com.mobileai.luncert.utils;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Redis {

    private static final String REDIS_SERVER  = "101.200.37.220";

    private static final String REDIS_AUTH = "Luncert428";

    private Jedis redis;

    public Redis() {
        redis = new Jedis(REDIS_SERVER);
        redis.auth(REDIS_AUTH);
    }

    public void setPair(String key, String value) { redis.set(key, value); }

    public String getValue(String key) { return redis.get(key); }

    public void setList(String key, List<String> values) { for (String value : values) redis.lpush(key, value); }

    public List<String> getList(String key, int start, int end) { return redis.lrange(key, start, end); }

    public Set<String> queryKeys() { return redis.keys("*"); }

    public Set<String> queryKeys(String pattern) { return redis.keys(pattern); }

    public void subscribe(JedisPubSub jedisPubSub, final String ... channels) { redis.subscribe(jedisPubSub, channels); }

    public void publish(String channel, String message) { redis.publish(channel, message); }

    public void close() { 
        redis.close();
        redis = null;
    }

}