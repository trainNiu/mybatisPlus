//package com.example.aicaiframework.demos.redis;
//
//
//import com.example.aicaiframework.demos.utils.ValidationUtil;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.core.*;
//import org.springframework.data.redis.core.script.DefaultRedisScript;
//
//import java.io.*;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
///**
// * redis
// *
// * @param <V>
// */
//public class RedisTemplateAdapter<V> extends RedisTemplate {
//
//    private static final int NUMBER_ONE = 1;
//    private static final int NUMBER_MINUS_ONE = -1;
//    private static final String DEFAULT_CHARSET = "UTF-8";
//
//
//    /**
//     * redis分布式锁
//     *
//     * @param key
//     * @param value
//     * @param timeout
//     * @return
//     */
//    public boolean lock(String key, V value, long timeout) {
//        return this.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 指定缓存失效时间
//     *
//     * @param key  键
//     * @param time 时间(秒)
//     * @return
//     */
//    public boolean expire(String key, long time) {
//        try {
//            if (time > 0) {
//                this.expire(key, time, TimeUnit.SECONDS);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 删除缓存
//     *
//     * @param key 可以传一个值 或多个
//     */
//    public Long batchDelete(String... key) {
//        if (key != null && key.length > 0) {
//            return this.delete(Arrays.asList(key));
//        }
//        return 0L;
//    }
//
//    /**
//     * 删除缓存
//     *
//     * @param key 可以传一个值
//     */
//    public boolean del(String key) {
//        if (key != null) {
//            return this.delete(key);
//        }
//        return false;
//    }
//
//    public Long delByPrefix(String keyPrefix) {
//        Set<String> keys = this.keys(keyPrefix + "*");
//        if (keys != null && keys.size() > 0) {
//            return this.delete(keys);
//        }
//        return 0L;
//    }
//
//
//    //region  GET SET
//
//    /**
//     * get
//     *
//     * @param k
//     * @return
//     */
//    public <V> V get(String k) {
//        if (ValidationUtil.isEmpty(k)) {
//            return null;
//        }
//
//        ValueOperations<String, V> operations = this.opsForValue();
//        return operations.get(k);
//    }
//
//    /**
//     * set 指定有效期
//     *
//     * @param k       redis key
//     * @param v       值
//     * @param timeout 单位秒 例如设置的是10秒失效，十秒之内查询有结果，十秒之后返回为null
//     */
//    public <V> void set(String k, V v, long timeout) {
//        ValueOperations<String, V> operations = this.opsForValue();
//        operations.set(k, v, timeout, TimeUnit.SECONDS);
//    }
//
//
//    /**
//     * set 永久
//     *
//     * @param k redis key
//     * @param v 值
//     */
//    public <V> void set(String k, V v) {
//        ValueOperations<String, V> operations = this.opsForValue();
//        operations.set(k, v);
//    }
//
//    /**
//     * 只有在 key 不存在时设置 key 的值
//     *
//     * @param key
//     * @param value
//     * @return 之前已经存在返回false, 不存在返回true
//     */
//    public boolean setIfAbsent(String key, String value) {
//        ValueOperations<String, String> operations = this.opsForValue();
//        Boolean absent = operations.setIfAbsent(key, value);
//        return absent != null && absent;
//    }
//    //endregion
//
//
//    //region Hash get set
//
//    /**
//     * set
//     * 全量替换map
//     *
//     * @param hkv
//     */
//    public <HV> void hashSet(String k, Map<String, HV> hkv) {
//        HashOperations<String, String, Object> hvHashOperations = this.opsForHash();
//        // 删除 无效的数据
//        if (hasKey(k)) {
//            Set<String> hkeys = hvHashOperations.keys(k);
//            if (hkeys != null && !hkeys.isEmpty()) {
//                hkeys.removeAll(hkv.keySet());
//                if (!hkeys.isEmpty()) {
//                    hvHashOperations.delete(k, hkeys.toArray(new String[hkeys.size()]));
//                }
//            }
//            //this.delete(k);
//        }
//
//        hvHashOperations.putAll(k, hkv);
//    }
//
//    /**
//     * 指定时间 全量替换 map
//     *
//     * @param key
//     * @param hkv
//     * @param expireSecond
//     * @param <HV>
//     * @return
//     */
//    public <HV> boolean hashSet(String key, Map<String, HV> hkv, long expireSecond) {
//        try {
//            hashSet(key, hkv);
//            expire(key, expireSecond);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public <HV> void hashPut(String k,String field,HV value) {
//        this.opsForHash().put(k,field,value);
//    }
//
//    /**
//     * put
//     *
//     * @param hkv
//     */
//    public <HV> void hashPut(String k, Map<String, HV> hkv) {
//        HashOperations<String, String, Object> hvHashOperations = this.opsForHash();
//        hvHashOperations.putAll(k, hkv);
//    }
//    /**
//     * put
//     *
//     * @param hkv
//     */
//    public <HV> void hashPutIfAbsent(String k, String hk, HV hkv) {
//        HashOperations<String, String, Object> hvHashOperations = this.opsForHash();
//        hvHashOperations.putIfAbsent(k, hk, hkv);
//    }
//
//    /**
//     * put
//     *
//     * @param hkv
//     */
//    public <HV> boolean hashPut(String k, Map<String, HV> hkv, long expireSecond) {
//        try {
//            hashPut(k, hkv);
//            expire(k, expireSecond);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * set
//     *
//     * @param k
//     * @param hk
//     * @param hv
//     * @param <HV>
//     */
//    public <HV> void hashSet(String k, String hk, HV hv) {
//        HashOperations<String, String, HV> hvHashOperations = this.opsForHash();
//        hvHashOperations.put(k, hk, hv);
//    }
//
//    /**
//     * set
//     *
//     * @param k
//     * @param hk
//     * @param hv
//     * @param <HV>
//     */
//    public <HV> void hashSet(String k, String hk, HV hv, long expireSecond) {
//        HashOperations<String, String, HV> hvHashOperations = this.opsForHash();
//        hvHashOperations.put(k, hk, hv);
//        expire(k, expireSecond);
//    }
//
//
//    /**
//     * get
//     *
//     * @param k
//     * @param hk
//     * @param <HV>
//     * @return
//     */
//    public <HV> List<HV> hashGet(String k, Collection<String> hk) {
//        HashOperations<String, String, HV> hvHashOperations = this.opsForHash();
//        return hvHashOperations.multiGet(k, hk);
//    }
//
//    /**
//     * get
//     *
//     * @param k
//     * @param hk
//     * @param <HV>
//     * @return
//     */
//    public <HV> HV hashGet(String k, String hk) {
//        HashOperations<String, String, HV> hvHashOperations = this.opsForHash();
//        if (hvHashOperations.hasKey(k, hk)) {
//            return hvHashOperations.get(k, hk);
//        }
//        return null;
//    }
//
//    /**
//     * get
//     *
//     * @param k
//     * @return
//     */
//    public <HK, HV> Map<HK, HV> hashGet(String k) {
//        return this.opsForHash().entries(k);
//    }
//
//    //endregion
//
//
//    /**
//     * 递增
//     *
//     * @param key   键
//     * @param delta 要增加几(大于0)
//     * @return
//     */
//    public long incr(String key, long delta) {
//        if (delta < 0) {
//            throw new RuntimeException("递增因子必须大于0");
//        }
//        return this.opsForValue().increment(key, delta);
//    }
//
//    /**
//     * 递增
//     *
//     * @param key 键
//     * @return
//     */
//    public long incr(String key) {
//        return this.opsForValue().increment(key, NUMBER_ONE);
//    }
//
//    /**
//     * 递减
//     *
//     * @param key   键
//     * @param delta 要减少几(小于0)
//     * @return
//     */
//    public long decr(String key, long delta) {
//        if (delta < 0) {
//            throw new RuntimeException("递减因子必须大于0");
//        }
//        return this.opsForValue().increment(key, -delta);
//    }
//
//    /**
//     * 递减
//     *
//     * @param key 键
//     * @return
//     */
//    public long decr(String key) {
//        return this.opsForValue().increment(key, NUMBER_MINUS_ONE);
//    }
//
//
//    /**
//     * 根据key 获取过期时间
//     *
//     * @param key 键 不能为null
//     * @return 时间(秒) 返回0代表为永久有效
//     */
//    public long getExpire(String key) {
//        return this.getExpire(key, TimeUnit.SECONDS);
//    }
//
//
//    /**
//     * 存储一个对象：key value均以字节码的形式设置
//     * 目前主要在session会话使用
//     *
//     * @param key   键
//     * @param value 值
//     * @return true成功 false失败
//     */
//    public boolean setObjectWithExpire(String key, int expireSecond, V value) {
//        return (boolean) execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                try {
//                    byte[] keyBytes = key.getBytes(DEFAULT_CHARSET);
//                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream(2048);
//                    if (!(value instanceof Serializable)) {
//                        throw new IllegalArgumentException("非法参数 [" + value.getClass().getName() + "]不支持序列化操作");
//                    }
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
//                    objectOutputStream.writeObject(value);
//                    objectOutputStream.flush();
//                    byte[] valueBytes = byteStream.toByteArray();
//                    return connection.setEx(keyBytes, expireSecond, valueBytes);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//        });
//    }
//
//    /**
//     * 获取一个对象：key 以字节码的形式设置
//     * 目前主要在session会话使用
//     *
//     * @param key   键
//     * @param clazz 获取对象类型
//     * @param <T>   类型限定
//     * @return 类型限定实例
//     */
//    public <T> T getObject(String key, Class<T> clazz) {
//        return (T) execute(new RedisCallback<T>() {
//            @Override
//            public T doInRedis(RedisConnection connection) throws DataAccessException {
//                try {
//                    byte[] keyBytes = key.getBytes(DEFAULT_CHARSET);
//                    byte[] valueBytes = connection.get(keyBytes);
//                    if (valueBytes != null && valueBytes.length > 0) {
//                        ByteArrayInputStream byteStream = new ByteArrayInputStream(valueBytes);
//                        ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
//                        Object result = objectInputStream.readObject();
//                        return clazz.cast(result);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        });
//    }
//
//    /**
//     * 执行lua脚本的函数，确保原子性
//     *
//     * @param script
//     * @param keys
//     * @param args
//     * @param <T>
//     * @return
//     */
//    public <T> T executeScript(DefaultRedisScript<T> script, List<String> keys, Object args) {
//        return (T) execute(script, keys, args);
//    }
//
//
//    /**
//     * 从key的列表中获取begin和end之间的元素。
//     *
//     * @param key
//     * @param start 开始位置, 0是开始位置
//     * @param end   结束位置, -1返回所有
//     * @return
//     */
//    public List lRange(String key, long start, long end) {
//        return this.opsForList().range(key, start, end);
//    }
//
//
//    /**
//     * 获取存储在key的列表的大小。
//     *
//     * @param key
//     * @return
//     */
//    public Long lLen(String key) {
//        return this.opsForList().size(key);
//    }
//
//
//    /**
//     * 将value添加到key,存储在list头部
//     *
//     * @param key
//     * @return
//     */
//    public Long lLeftPush(String key, String value) {
//        return this.opsForList().leftPush(key, value);
//    }
//
//
//    /**
//     * 添加元素,有序集合是按照元素的score值由小到大排列
//     *
//     * @param key
//     * @param value
//     * @param score
//     * @return
//     */
//    public Boolean zAdd(String key, String value, double score) {
//        return this.opsForZSet().add(key, value, score);
//    }
//
//    /**
//     * @param key
//     * @param values
//     * @return
//     */
//    public void zAdd(String key, Set<ZSetOperations.TypedTuple<String>> values, long expireSecond) {
//        try {
//            this.opsForZSet().add(key, values);
//            expire(key, expireSecond);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * @param key
//     * @param values
//     * @return
//     */
//    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<String>> values) {
//        return this.opsForZSet().add(key, values);
//    }
//
//
//    /**
//     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
//     *
//     * @param key
//     * @param value
//     * @return 0表示第一位
//     */
//    public Long zRank(String key, Object value) {
//        return this.opsForZSet().rank(key, value);
//    }
//
//    /**
//     * 获取集合的元素, 从小到大排序
//     *
//     * @param key
//     * @param start 开始位置
//     * @param end   结束位置, -1查询所有
//     * @return
//     */
//    public Set<String> zRange(String key, long start, long end) {
//        return this.opsForZSet().range(key, start, end);
//    }
//
//
//    /**
//     * 从从高到低排序的有序集合中获取从start到end范围内的元素。
//     *
//     * @param key
//     * @param start 开始位置
//     * @param end   结束位置, -1查询所有
//     * @return
//     */
//    public Set<String> zReverseRange(String key, long start, long end) {
//        return this.opsForZSet().reverseRange(key, start, end);
//    }
//
//    /**
//     * 根据Score值查询集合元素
//     *
//     * @param key
//     * @param min 最小值
//     * @param max 最大值
//     * @return
//     */
//    public Set<String> zRangeByScore(String key, double min, double max) {
//        return this.opsForZSet().rangeByScore(key, min, max);
//    }
//
//    /**
//     * 查询指定Score范围的元素
//     * @param key
//     * @param min 最小值
//     * @param max 最大值
//     * @param offset 偏移量
//     * @param count 返回的元素个数
//     * @return
//     */
//    public Set<String> zRangeByScore(String key,double min,double max,long offset,long count) {
//        return this.opsForZSet().rangeByScore(key,min,max,offset,count);
//    }
//
//    /**
//     * 获取集合大小
//     *
//     * @param key
//     * @return
//     */
//    public Long zSize(String key) {
//        return this.opsForZSet().size(key);
//    }
//
//    /**
//     * 删除集合
//     *
//     * @param key
//     * @param values
//     * @return
//     */
//    public Long zRemove(String key, Object... values) {
//        return this.opsForZSet().remove(key, values);
//    }
//
//
//}
