package com.store.dev.repository.commons;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 * @author 铭
 * 2018/11/17 8:46
 */
public class JedisUtil {
    // 定义一个连接池对象
    private static final JedisPool POOL;

    static {
        // 1.设置连接池的配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置连接池的最大连接数【可选】
        config.setMaxTotal(50);
        // 设置空闲时池中保有的最大连接数【可选】
        config.setMaxIdle(10);
        // 2.设置连接池对象
        POOL = new JedisPool(config, "127.0.0.1", 6379);
    }

    /**
     * 从池中获取连接
     */
    public static Jedis getJedis() {
        return POOL.getResource();
    }

    /**
     * 将redis存放数据的方式做进一步封装，使其更加适合java数据的存放 此方法将javaBean直接存储为一个字符串
     *
     * @param key        存储的名字
     * @param javaBean   要存储的对象
     * @param activeTime 该对象的有效时间，单位为秒
     */

    public static Boolean setBean(String key, Object javaBean,
                                  Integer activeTime) {
        /**
         * 1.获取jedis对象 2.将对象转成json数据存入redis缓存 3.释放资源
         */
        if (javaBean != null && key != null && key != "") {
            Jedis jedis = POOL.getResource();
            jedis.set(key, JSONObject.fromObject(javaBean).toString());
            if (activeTime != null && activeTime > 0) {
                jedis.expire(key, activeTime);
            }
            jedis.close();
            return true;
        }
        return false;
    }

    /**
     * 此方法将会把存在redis中的数据取出来，并封装成相应的JavaBean
     *
     * @param key       存储的名字
     * @param beanClass 要封装成为的javaBean
     * @return javaBean
     */
    public static Object getBean(String key, Class beanClass) {
        if (key != null && key != "" && beanClass != null) {
            Jedis jedis = POOL.getResource();
            Object object = JSONObject.toBean(
                    JSONObject.fromObject(jedis.get(key)), beanClass);
            jedis.close();
            return object;
        }
        return null;
    }

    /**
     * 此方法将ArrayList集合直接存储为一个字符串
     *
     * @param key        存储的名字
     * @param list       要存储的集合对象
     * @param activeTime 该对象的有效时间，单位为秒
     */
    public static Boolean setArrayList(String key, List<Object> list,
                                       Integer activeTime) {
        if (list != null && key != null && key != "") {
            Jedis jedis = POOL.getResource();
            jedis.set(key, JSONArray.fromObject(list).toString());
            if (activeTime != null && activeTime > 0) {
                jedis.expire(key, activeTime);
            }
            jedis.close();
            return true;
        }
        return false;
    }

    /**
     * 此方法将会把存在redis中的数据取出来，并封装成相应的Arraylist集合
     *
     * @param key       存储的名字
     * @param beanClass 要封装成为的javaBean
     * @return List
     */
    public static List<Object> getArraylist(String key, Class beanClass) {
        if (key != null && key != "" && beanClass != null) {
            Jedis jedis = POOL.getResource();
            JSONArray jsonArray = JSONArray.fromObject(jedis.get(key));
            List<Object> list = new ArrayList<Object>();
            for (int i = 0; i < jsonArray.size(); i++) {
                Object object = JSONObject.toBean(
                        (JSONObject) jsonArray.get(i), beanClass);
                list.add(object);
            }
            return list;
        }
        return null;
    }

    /**
     * 此方法将Map集合直接存储为一个字符串
     *
     * @param key        存储的名字
     * @param map        要存储的Map集合对象
     * @param activeTime 该对象的有效时间，单位为秒
     * @return 成功返回true, 失败返回false
     */
    public static boolean setMap(String key, Map<String, Object> map,
                                 Integer activeTime) {
        if (map != null && key != null && key != "") {
            Jedis jedis = POOL.getResource();
            jedis.set(key, JSONObject.fromObject(map).toString());
            if (activeTime != null && activeTime > 0) {
                jedis.expire(key, activeTime);
            }
            jedis.close();
            return true;
        }
        return false;
    }

    /**
     * 此方法将会把存在redis中的数据取出来，并封装成相应的Map集合
     *
     * @param key       存储的名字
     * @param beanClass 要封装成的对象
     * @return 返回封装后的map集合
     */
    public static Map<String, Object> getMap(String key, Class beanClass) {
        if (key != null && key != "" && beanClass != null) {
            Jedis jedis = POOL.getResource();
            Map map1 = (Map) JSONObject.fromObject(jedis.get(key));
            Set set = map1.keySet();
            Iterator ite = set.iterator();
            Map<String, Object> maps = new HashMap<String, Object>();
            while (ite.hasNext()) {
                String key1 = (String) ite.next();
                JSONObject jsonObject = JSONObject.fromObject(map1.get(key1));
                Object object = JSONObject.toBean(jsonObject, beanClass);
                maps.put(key1, object);
            }
            return maps;
        }
        return null;
    }
}
