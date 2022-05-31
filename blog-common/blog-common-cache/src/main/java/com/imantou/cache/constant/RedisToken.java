package com.imantou.cache.constant;

/**
 * token连接符
 *
 * @author gaobug
 */
public interface RedisToken {
    /**
     * 参考CacheKeyPrefix
     * cacheNames 与 key 之间的默认连接字符
     */
    String PLATFORM_AUTH_BUCKET = "blog:platform:token:";

    /**
     * key内部的连接字符（自定义）
     */
    String UNION_KEY = ":";

}
