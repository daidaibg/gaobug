package com.imantou.auth.service.Impl;

import com.imantou.response.enums.ResultEnum;
import com.imantou.response.exception.BusinessException;
import com.imantou.auth.service.AuthService;
import com.imantou.auth.vo.JwtTokenVO;
import com.imantou.auth.vo.UserContext;
import com.imantou.cache.util.RedisUtil;


import com.imantou.utils.JwtUtils;
import com.imantou.utils.RequestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 鉴权服务实现
 *
 * @author gaobug
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public JwtTokenVO authorization() {
        HttpServletRequest request = RequestUtils.getRequest();
        if (request == null) {
            throw new BusinessException("request cant be null");
        }
        String authToken = request.getHeader("auth_token");
        if (!StringUtils.hasText(authToken)) {
            throw new BusinessException(ResultEnum.LOGON_TOKEN_EXPIRE);
        }
        UserContext userContext = RedisUtil.get(authToken);
        if (null == userContext) {
            throw new BusinessException(ResultEnum.LOGON_TOKEN_EXPIRE);
        }
        // TODO: 2022/5/12 这里后面做鉴权
        return new JwtTokenVO(JwtUtils.createToken(authToken));
    }

}
