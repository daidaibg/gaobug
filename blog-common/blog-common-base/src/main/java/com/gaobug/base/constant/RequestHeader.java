package com.gaobug.base.constant;

/**
 * @author gaobug
 * @date 2022/6/2 12:40
 */
public interface RequestHeader {
    /**
     * 网关校验登录请求头token令牌
     */
    String AUTH_TOKEN_HEADER = "auth-token";
    /**
     * 服务用户信息请求头jwt令牌
     */
    String AUTH_JWT_HEADER = "auth-jwt";
    /**
     * 应用ID
     */
    String APP_ID_HEADER = "app-id";
    /**
     * 参数签名
     */
    String SIGN_HEADER = "sign";
    /**
     * 时间戳
     */
    String TIMESTAMP = "timestamp";
}
