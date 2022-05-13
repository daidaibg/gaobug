package com.imantou.auth.service;

import com.imantou.common.domain.JwtTokenVO;

public interface AuthService {
    /**
     * 用户鉴权
     *
     * @return 请求结果
     */
    JwtTokenVO authorization();
}
