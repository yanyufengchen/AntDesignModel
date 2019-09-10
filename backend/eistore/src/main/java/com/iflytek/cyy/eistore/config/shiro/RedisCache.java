package com.iflytek.cyy.eistore.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Shiro Cache对象
 *
 * @author liuht
 * 2019/4/29 16:24
 */
@SuppressWarnings("unchecked")
public class RedisCache<K, V> implements Cache<K, V> {

    private RedisTemplate redisTemplate;

    private String prefix;

    private long timeout;

    public RedisCache(String prefix, RedisTemplate redisTemplate, long timeout) {
        this.prefix = prefix;
        this.redisTemplate = redisTemplate;
        this.timeout = timeout;
    }

    private String getKey(K k) {
        return this.prefix + k;
    }

    @Override
    public V get(K k) throws CacheException {
        if (k == null) {
            return null;
        }
        return (V) redisTemplate.opsForValue().get(getKey(k));

    }

    @Override
    public V put(K k, V v) throws CacheException {
        if (k == null || v == null) {
            return null;
        }

        redisTemplate.opsForValue().set(getKey(k), v, timeout, TimeUnit.SECONDS);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        if (k == null) {
            return null;
        }
        V v = (V) redisTemplate.opsForValue().get(getKey(k));
        redisTemplate.delete(getKey(k));
        return v;
    }

    @Override
    public void clear() throws CacheException {
        final Set<K> keys = keys();
        for (K key : keys) {
            remove(key);
        }
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        Set<byte[]> keys = redisTemplate.keys(prefix + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        Set<K> sets = new HashSet<>(keys.size());
        for (byte[] key : keys) {
            sets.add((K) key);
        }
        return sets;
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<>(keys.size());
        for (K k : keys) {
            values.add(get(k));
        }
        return values;
    }
}
