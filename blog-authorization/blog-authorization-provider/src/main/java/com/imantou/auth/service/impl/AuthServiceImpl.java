package com.imantou.auth.service.impl;

import com.imantou.auth.service.AuthService;
import com.imantou.common.domain.AuthTokenVO;
import com.imantou.common.domain.JwtTokenVO;
import com.imantou.common.domain.UserContext;
import com.imantou.common.enums.ResultEnum;
import com.imantou.common.exception.BusinessException;
import com.imantou.common.utils.JwtUtils;
import com.imantou.common.utils.RedisHelper;
import com.imantou.common.utils.RequestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 鉴权服务实现
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private RedisHelper redisHelper;

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
        UserContext userContext = (UserContext) redisHelper.get(authToken);
        if (null == userContext) {
            throw new BusinessException(ResultEnum.LOGON_TOKEN_EXPIRE);
        }
        // TODO: 2022/5/12 这里后面做鉴权
        return new JwtTokenVO(JwtUtils.createToken(authToken));
    }

}
