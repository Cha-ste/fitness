package com.ocean.utils;

import redis.clients.jedis.Jedis;

/**
 * redis 工具类
 */
public class JedisUtils {
    private static final String REDIS_SERVER_IP = "127.0.0.1";
    private static final int REDIS_SERVER_PORT = 6379;
    private static Jedis jedis = new Jedis(REDIS_SERVER_IP, REDIS_SERVER_PORT);

    public static Jedis getJedis() {
        return jedis;
    }

    public static void setString(String key, String value) {
        jedis.set(key, value);
    }

    public static void delString(String key) {
        jedis.del(key);
    }

    public static String getString(String key) {
        return jedis.get(key);
    }

    public static void setListExpired(String key, int second, String value) {
        jedis.setex(key, second, value);
    }

    public static void setList(String key, String value){
        jedis.lpush(key, value);
    }

    public static Long delList(String key, String value){
        return jedis.lrem(key, 1, value);
    }

}
