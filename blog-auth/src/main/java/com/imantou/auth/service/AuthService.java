package com.gaobug.auth.service;


import com.gaobug.auth.vo.JwtTokenVO;

/**
 * @author gaobug
 */
public interface AuthService {
    /**
     * 用户鉴权
     *
     * @return 请求结果
     */
    JwtTokenVO authorization();
}
