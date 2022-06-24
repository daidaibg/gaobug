package com.gaobug.platform.api;

import cn.hutool.core.bean.BeanUtil;
import com.gaobug.api.dto.UserRegisterDTO;
import com.gaobug.api.user.PlatformUserClient;
import com.gaobug.api.vo.PlatformUserVO;
import com.gaobug.base.utils.SnowflakeUtils;
import com.gaobug.platform.domain.PlatformUser;
import com.gaobug.platform.service.UserService;
import com.gaobug.response.exception.BusinessException;
import com.gaobug.utils.security.EncryptUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

/**
 * 博客平台用户api
 *
 * @author gaobug
 */
@RefreshScope
@RestController
public class PlatformUserApi implements PlatformUserClient {
    @Value("${gaobug.url.avatar}")
    private String avatar;
    @Resource
    private UserService userService;

    @Override
    public PlatformUserVO getUserByAccountOrEmail(String account) {
        PlatformUser platformUser = userService.getUserByAccountOrEmail(account);
        return getPlatformUserVO(platformUser);
    }

    @Override
    public PlatformUserVO getUserByMobile(String mobile) {
        PlatformUser platformUser = userService.lambdaQuery()
                .eq(PlatformUser::getMobile, mobile)
                .last("limit 1").one();
        return getPlatformUserVO(platformUser);
    }

    private PlatformUserVO getPlatformUserVO(PlatformUser platformUser) {
        if (null == platformUser) {
            return null;
        }
        PlatformUserVO platformUserVO = new PlatformUserVO();
        BeanUtils.copyProperties(platformUser, platformUserVO);
        return platformUserVO;
    }

    @Override
    public PlatformUserVO saveRegisterUser(UserRegisterDTO platformRegister) {
        if (StringUtils.hasText(platformRegister.getEmail())) {
            PlatformUser one = userService.lambdaQuery()
                    .select(PlatformUser::getId)
                    .eq(PlatformUser::getEmail, platformRegister.getEmail())
                    .last("limit 1")
                    .one();
            if (null != one) {
                throw new BusinessException("邮箱已被注册");
            }
        }

        if (StringUtils.hasText(platformRegister.getMobile())) {
            PlatformUser one = userService.lambdaQuery()
                    .select(PlatformUser::getId)
                    .eq(PlatformUser::getMobile, platformRegister.getMobile())
                    .last("limit 1")
                    .one();
            if (null != one) {
                throw new BusinessException("手机已被注册");
            }
        }
        PlatformUser platformUserForSave = new PlatformUser();
        BeanUtil.copyProperties(platformRegister, platformUserForSave);
        // 处理密码
        String salt = SnowflakeUtils.nextStr();
        String password = Optional.ofNullable(platformRegister.getPassword()).orElse("www.gaobug.com");
        platformUserForSave.setPassword(EncryptUtils.sha256(password, salt));
        platformUserForSave.setSalt(salt);
        platformUserForSave.setAvatar(avatar);
        platformUserForSave.setCreateTime(new Date());
        platformUserForSave.setUpdateTime(new Date());
        userService.save(platformUserForSave);
        return BeanUtil.copyProperties(platformUserForSave, PlatformUserVO.class);
    }
}
