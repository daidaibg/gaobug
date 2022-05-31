package imantou.user.service.Impl;

import imantou.user.api.SysUserService;
import imantou.common.domain.SysUser;
import imantou.common.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 用户服务回滚
 *
 * @author gaobug
 */
@Component
public class UserServiceFallbackImpl implements SysUserService {

    @Override
    public SysUser getUserByName(String userName) {
        throw new BusinessException("获取用户信息失败！");
    }

    @Override
    public Set<String> getUserPermissions(Integer userId) {
        throw new BusinessException("获取用户权限失败！");
    }
}
