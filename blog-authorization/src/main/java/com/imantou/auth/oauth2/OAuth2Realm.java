package com.imantou.auth.oauth2;



import com.imantou.auth.service.SysUserService;
import com.imantou.common.domain.SysUser;
import com.imantou.common.domain.UserContext;
import com.imantou.common.enums.ResultEnum;
import com.imantou.common.enums.StateEnum;
import com.imantou.common.exception.BusinessException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author yhht
 * 认证
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Resource
    private SysUserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserContext user = (UserContext) principals.getPrimaryPrincipal();
        //用户权限列表
        Set<String> permsSet = userService.getUserPermissions(user.getUserId());
        return new SimpleAuthorizationInfo(permsSet);
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws BusinessException {
//        String accessToken = (String) token.getPrincipal();
//        //根据accessToken，查询用户信息
//        UserContext user = (UserContext) redisUtils.get(RedisCache.USER_TOKEN_KEY_ + accessToken);
//        //token失效
//        if (user == null) {
//            throw new BusinessException(ResultEnum.LOGON_TOKEN_EXPIRE);
//        }
//        //获取最新的用户信息
//        UserContext userContext = userService.queryUserById(user.getUserId());
//        //账号锁定
//        if (StateEnum.FORBIDDEN.getValue().equals(userContext.getStatus())) {
//            throw new BusinessException("账号已被锁定,请联系管理员");
//        }
//        //更新key60分钟过期
//        redisUtils.set(RedisCache.USER_TOKEN_KEY_+accessToken,userContext, RedisCache.USER_EXPIRE_TIME, TimeUnit.MINUTES);
//        ThreadLocalContextHolder.getThreadLocalContext().setUserContext(user);
        return new SimpleAuthenticationInfo(new SysUser(), "accessToken", getName());
    }

}
