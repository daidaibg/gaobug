package com.imantou.api.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 身份令牌
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class JwtTokenVO {
    public static final String AUTHORITY_TOKEN = "authority-token";
    /**
     * 身份令牌
     */
    private final String authorityToken;
}
