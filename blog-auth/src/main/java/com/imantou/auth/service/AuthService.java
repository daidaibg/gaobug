package com.imantou.auth.service;


import com.imantou.auth.vo.JwtTokenVO;

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
