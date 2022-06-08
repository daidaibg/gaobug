package com.imantou.security.handler;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求参数验签处理器接口类
 *
 * @author gaobug
 */
public interface IParamsSignHandler {

    /**
     * 请求验签
     *
     * @param request {@link HttpServletRequest}
     * @return 验签结果
     */
    boolean doCheckSign(HttpServletRequest request);

}
