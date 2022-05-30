package com.imantou.security.handler;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求参数验签处理器接口类
 */
public interface IParamsSignHandler {

    /**
     * Get 请求验签
     *
     * @param request {@link HttpServletRequest}
     * @return
     */
    boolean doGet(HttpServletRequest request);

    /**
     * Post 请求验签
     *
     * @param request {@link HttpServletRequest}
     * @return
     */
    boolean doPost(HttpServletRequest request);
}
