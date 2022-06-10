package com.imantou.cache.constant;

/**
 * token前缀
 *
 * @author gaobug
 */
public interface AuthToken {
    /**
     * jwt令牌
     */
    String AUTH_JWT_BUCKET = "auth:jwt:";
    /**
     * 登录令牌
     */
    String AUTH_TOKEN_BUCKET = "auth:token:";
}
