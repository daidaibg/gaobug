package com.imantou.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录方式
 *
 * @author hzq
 * @date 2022/6/2 16:54
 */
@Getter
@AllArgsConstructor
public enum LoginType {
    /**
     * 手机
     */
    mobile(0),
    /**
     * 密码
     */
    password(1),
    /**
     * 微信
     */
    wechat(3),
    ;
    private Integer type;

}
