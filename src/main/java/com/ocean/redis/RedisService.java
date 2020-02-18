package com.ocean.redis;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
    @Autowired
    @Qualifier(value = "MyJedisPool")
    JedisPool jedisPool;

    /**
     * 获取单个key值
     * @param prefix 前缀
     * @param key 键
     * @param clazz 类型
     * @param <T> 类型
     * @return clazz 对象
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.prefix() + key;
            String value = jedis.get(realKey);
            return stringToBean(value, clazz);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置值
     *
     * @param prefix 前缀
     * @param key 键
     * @param value 值
     * @param <T> 类型
     * @return 设置结果 true：成功，false：失败
     */
    public <T> Boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.prefix() + key;
            String str = beanToString(value);
            jedis.set(realKey, str);
        } catch (Exception e){
            e.getStackTrace();
            return false;
        }finally {
            returnToPool(jedis);
        }
        return true;
    }

    /**
     * 设置值并设置键过期时间
     *
     * @param prefix 前缀
     * @param key 键
     * @param value 值
     * @param <T> 类型
     * @param second 有效期
     * @return 设置结果 true：成功，false：失败
     */
    public <T> Boolean set(KeyPrefix prefix, String key, T value, int second) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.prefix() + key;
            String str = beanToString(value);
            jedis.set(realKey, str);
            jedis.expire(realKey, second);
        } catch (Exception e){
            e.getStackTrace();
            return false;
        }finally {
            returnToPool(jedis);
        }
        return true;
    }

    /**
     * 给指定key值加一（原子操作）
     *
     * @param prefix 前缀
     * @param key 键
     * @return 操作结果 true：成功，false：失败
     */
    public Boolean incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.prefix() + key;
            jedis.incr(realKey);
        } catch (Exception e){
            e.getStackTrace();
            return false;
        }finally {
            returnToPool(jedis);
        }
        return true;
    }

    /**
     * 给指定key值减一（原子操作）
     *
     * @param prefix 前缀
     * @param key 键
     * @return 操作结果 true：成功，false：失败
     */
    public Boolean decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.prefix() + key;
            jedis.decr(realKey);
        } catch (Exception e){
            e.getStackTrace();
            return false;
        }finally {
            returnToPool(jedis);
        }
        return true;
    }

    /**
     * 判断是否存在key值
     *
     * @param prefix 前缀
     * @param key 键
     * @return 操作结果 true：成功，false：失败
     */
    public Boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.prefix() + key;
            jedis.exists(realKey);
        } catch (Exception e){
            e.getStackTrace();
            return false;
        }finally {
            returnToPool(jedis);
        }
        return true;
    }

    /**
     * 对象转json字符串
     *
     * @param value 转换目标
     * @param <T> 对象类型
     * @return json
     */
    private <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }

        Class clazz = value.getClass();
        if(clazz == Integer.class || clazz == int.class) {
            return String.valueOf(value);
        } else if(clazz == Long.class || clazz == long.class) {
            return String.valueOf(value);
        } else if(clazz == String.class) {
            return value.toString();
        } else {
            return JSON.toJSONString(value);
        }

    }

    /**
     * 字符串转换成对象
     *
     * @param value 转换目标
     * @param clazz 转换对象类型
     * @param <T> 对象类型
     * @return 对象实例
     */
    @SuppressWarnings("unchecked")
     private <T> T stringToBean(String value, Class<T> clazz) {
        if(value == null || value.length() == 0 || clazz == null) {
            return null;
        }

         if(clazz == Integer.class || clazz == int.class) {
             return (T)Integer.valueOf(value);
         } else if(clazz == Long.class || clazz == long.class) {
             return (T)Long.valueOf(value);
         } else if(clazz == String.class) {
             return (T)value;
         } else {
             return JSON.toJavaObject(JSON.parseObject(value), clazz);
         }

     }

   /**
     * 释放连接
     *
     * @param jedis redis连接
     * @return 关闭结果
     */
    private boolean returnToPool (Jedis jedis) {
        if(jedis == null) {
            return true;
        }
        if (jedis.isConnected()) {
            jedis.close();
        }
        return true;
    }
}
