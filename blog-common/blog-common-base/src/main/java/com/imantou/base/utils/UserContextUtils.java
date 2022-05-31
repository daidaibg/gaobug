package com.imantou.base.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户上下文工具类
 */
public class UserContextUtils {

    public static String getAuthToken() {
        HttpServletRequest request = RequestUtils.getRequest();
        if (request != null) {
            return request.getHeader("auth_token");
        }
        return null;
    }
}
