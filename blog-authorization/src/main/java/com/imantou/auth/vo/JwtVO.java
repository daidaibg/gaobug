package com.imantou.auth.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JwtVO {
    private Integer userId;
    private Long token;
}
