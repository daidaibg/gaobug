package com.imantou.auth.service;

import com.imantou.common.vo.JwtTokenVO;

public interface AuthService {
    /**
     * 用户鉴权
     *
     * @return 请求结果
     */
    JwtTokenVO authorization();
}
