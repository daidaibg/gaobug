package com.imantou.auth.service.Impl;

import com.imantou.response.ResponseWrapped;
import com.imantou.api.dto.PlatformUserDTO;
import com.imantou.api.user.PlatformUserClient;
import com.imantou.auth.dto.PlatformRegisterDTO;
import com.imantou.auth.service.RegisterService;
import com.imantou.base.utils.EncryptUtils;
import com.imantou.base.utils.SnowflakeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 注册服务
 *
 * @author gaobug
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private PlatformUserClient platformUserClient;

    @Override
    public ResponseWrapped<Object> registerUser(PlatformRegisterDTO form) {
        PlatformUserDTO platformUserDTO = new PlatformUserDTO();
        BeanUtils.copyProperties(form,platformUserDTO);
        // 处理密码
        String salt = SnowflakeUtils.nextStr();
        platformUserDTO.setPassword(EncryptUtils.sha256(form.getPassword(), salt));
        platformUserDTO.setSalt(salt);
        return platformUserClient.registerUser(platformUserDTO);
    }
}
