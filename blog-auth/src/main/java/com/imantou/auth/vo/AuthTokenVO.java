package com.imantou.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.function.Supplier;

/**
 * 登录令牌
 */
@Getter
@Setter
@ToString
public class AuthTokenVO {
    public static final String AUTH_TOKEN = "auth_token";

    public AuthTokenVO(String authToken, Date expiresTime) {
        this.authToken = authToken;
        this.expiresTime = expiresTime;
    }

    /**
     * 登录令牌
     */
    private final String authToken;
    /**
     * 失效时间
     */
    private Date expiresTime;
}
