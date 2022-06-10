package com.gaobug.base.context;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.Assert;

/**
 * 身份令牌上下文
 */
public class JwtContext {

    private static final ThreadLocal<DecodedJWT> DECODED_JWT_THREAD_LOCAL = new ThreadLocal<>();

    public static void putDecodedJWT(DecodedJWT decodedJWT) {
        DECODED_JWT_THREAD_LOCAL.set(decodedJWT);
    }

    /**
     * 获取用户标识
     */
    public static Long getUserId() {
        Long id = getClaim("id").asLong();
        Assert.notNull(id, "用户信息解析失败，身份标识缺失");
        return id;
    }

    /**
     * 获取用户名
     */
    public static String getUserName() {
        String username = getClaim("username").asString();
        Assert.notNull(username, "用户信息解析失败，身份信息缺失");
        return username;
    }

    public static Claim getClaim(String name) {
        DecodedJWT decodedJWT = getDecodedJWT();
        Assert.notNull(decodedJWT, "DecodedJWT can't be null");
        return decodedJWT.getClaim(name);
    }

    public static DecodedJWT getDecodedJWT() {
        return DECODED_JWT_THREAD_LOCAL.get();
    }

    public static void remove() {
        DECODED_JWT_THREAD_LOCAL.remove();
    }
}
