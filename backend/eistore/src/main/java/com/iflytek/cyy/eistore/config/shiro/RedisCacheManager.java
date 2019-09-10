package com.iflytek.cyy.eistore.config.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisCacheManager
 *
 * @author liuht
 * 2019/4/29 16:36
 */
public class RedisCacheManager extends AbstractCacheManager {

    private RedisTemplate redisTemplate;

    private String prefix;

    private long timeout;

    public RedisCacheManager(String prefix, RedisTemplate redisTemplate, long timeout) {
        this.prefix = prefix;
        this.redisTemplate = redisTemplate;
        this.timeout = timeout;
    }

    @Override
    protected Cache createCache(final String s) throws CacheException {
        return new RedisCache(prefix, redisTemplate, timeout);
    }
}
