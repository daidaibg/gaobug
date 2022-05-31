package com.imantou.security.handler;


import com.imantou.base.utils.JacksonUtils;

/**
 * 默认接口加密处理类
 */
public class DefaultRestEncryptHandler extends AbstractRestEncryptHandler {

    @Override
    public String toJson(Object body) {
        return JacksonUtils.toJSONString(body);
    }
}
