package com.imantou.platform.api;

import java.util.Date;

import com.imantou.api.dto.PlatformRegisterDTO;
import com.imantou.base.utils.EncryptUtils;
import com.imantou.base.utils.SnowflakeUtils;
import com.imantou.response.exception.BusinessException;
import com.imantou.response.ResponseWrapped;
import com.imantou.api.dto.PlatformUserDTO;
import com.imantou.api.user.PlatformUserClient;
import com.imantou.api.vo.PlatformUserVO;
import com.imantou.platform.domain.PlatformUser;
import com.imantou.platform.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
        PlatformUserVO platformUserVO = new PlatformUserVO();
        BeanUtils.copyProperties(platformUser, platformUserVO);
        return platformUserVO;

    }

    @Override
    public ResponseWrapped<Object> registerUser(PlatformRegisterDTO platformUserDTO) {
        PlatformUser platformUserForSave = new PlatformUser();
        platformUserForSave.setUsername(SnowflakeUtils.nextStr());
        // 处理密码
        String salt = SnowflakeUtils.nextStr();
        platformUserForSave.setPassword(EncryptUtils.sha256(platformUserDTO.getPassword(), salt));
        platformUserForSave.setSalt(salt);
        platformUserForSave.setGender(0);
        platformUserForSave.setAvatar(avatar);
        platformUserForSave.setEmail(platformUserDTO.getEmail());
        platformUserForSave.setBirthday(new Date());
        platformUserForSave.setNickName("路人甲");
        platformUserForSave.setCreateTime(new Date());
        platformUserForSave.setUpdateTime(new Date());
        userService.save(platformUserForSave);
        return ResponseWrapped.success();
    }
}
