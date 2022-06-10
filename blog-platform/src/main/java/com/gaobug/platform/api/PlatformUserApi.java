package com.gaobug.platform.api;

import cn.hutool.core.bean.BeanUtil;
import com.gaobug.api.dto.PlatformRegister;
import com.gaobug.api.user.PlatformUserClient;
import com.gaobug.api.vo.PlatformUserVO;
import com.gaobug.base.utils.SnowflakeUtils;
import com.gaobug.platform.domain.PlatformUser;
import com.gaobug.platform.service.UserService;
import com.gaobug.response.ResponseWrapped;
import com.gaobug.utils.EncryptUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
    public PlatformUserVO getUserByName(String userName) {
        PlatformUser platformUser = userService.lambdaQuery()
                .eq(PlatformUser::getUsername, userName)
                .last("limit 1").one();
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
    public ResponseWrapped<PlatformUserVO> registerUser(PlatformRegister platformRegister) {
        PlatformUser platformUserForSave = new PlatformUser();
        platformUserForSave.setUsername(SnowflakeUtils.nextStr());
        // 处理密码
        String salt = SnowflakeUtils.nextStr();
        String password = Optional.ofNullable(platformRegister.getPassword()).orElse("www.gaobug.com");
        platformUserForSave.setPassword(EncryptUtils.sha256(password, salt));
        platformUserForSave.setSalt(salt);
        platformUserForSave.setGender(0);
        platformUserForSave.setMobile(platformRegister.getMobile());
        platformUserForSave.setAvatar(avatar);
        platformUserForSave.setEmail(platformRegister.getEmail());
        platformUserForSave.setBirthday(new Date());
        platformUserForSave.setNickName("路人甲");
        platformUserForSave.setCreateTime(new Date());
        platformUserForSave.setUpdateTime(new Date());
        userService.save(platformUserForSave);
        return new ResponseWrapped<>(BeanUtil.copyProperties(platformUserForSave, PlatformUserVO.class));
    }
}
