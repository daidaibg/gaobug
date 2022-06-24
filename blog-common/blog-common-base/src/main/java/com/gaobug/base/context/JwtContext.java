package com.gaobug.base.context;

import cn.hutool.jwt.JWTPayload;
import org.springframework.util.Assert;

/**
 * 身份令牌上下文
 */
public class JwtContext {

    private static final ThreadLocal<JWTPayload> JWT_THREAD_LOCAL = new ThreadLocal<>();

    public static void putJWTPayload(JWTPayload jwtPayload) {
        JWT_THREAD_LOCAL.set(jwtPayload);
    }

    /**
     * 获取用户标识
     */
    public static Long getUserId() {
        Long id = (Long) JWT_THREAD_LOCAL.get().getClaim("id");
        Assert.notNull(id, "用户信息解析失败，身份标识缺失");
        return id;
    }

    /**
     * 获取用户名
     */
    public static String getUserName() {
        String username = String.valueOf(JWT_THREAD_LOCAL.get().getClaim("username"));
        Assert.notNull(username, "用户信息解析失败，身份信息缺失");
        return username;
    }

    public static JWTPayload getJWTPayload() {
        return JWT_THREAD_LOCAL.get();
    }

    public static void remove() {
        JWT_THREAD_LOCAL.remove();
    }
}
