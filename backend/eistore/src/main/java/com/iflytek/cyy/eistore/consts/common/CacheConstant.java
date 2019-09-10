package com.iflytek.cyy.eistore.consts.common;

/**
 * Cache 缓存常量
 *
 * @author liuht
 * @date 2018/8/17 17:02
 */
public interface CacheConstant {

    /**
     * 所有缓存前缀
     * 如果是注解添加缓存, 可以不用加,
     * 已在配置spring.cache.redis.key-prefix 中配置了此值
     */
    String KEY_PREFIX = "eistore:";

    /**
     * 默认缓存超时时间 30分钟
     */
    long DEFAULT_TIMEOUT = 30 * 60;

    /**
     * 用户权限缓存名称
     */
    String USER_PERMISSIONS = KEY_PREFIX + "ups:";

    /**
     * 用户session缓存名称
     */
    String USER_SESSIONS = KEY_PREFIX + "uss:";

    /**
     * 分布式锁
     */
    String DISTRIBUTE_LOCK = KEY_PREFIX + "lock";
}
