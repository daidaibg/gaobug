package com.imantou.platform.api;

import com.imantou.response.exception.BusinessException;
import com.imantou.response.ResponseWrapped;
import com.imantou.api.dto.PlatformUserDTO;
import com.imantou.api.user.PlatformUserClient;
import com.imantou.api.vo.PlatformUserVO;
import com.imantou.platform.domain.PlatformUser;
import com.imantou.platform.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 博客平台用户api
 *
 * @author gaobug
 */
@RestController
public class PlatformUserApi implements PlatformUserClient {
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
    public ResponseWrapped<Object> registerUser(PlatformUserDTO platformUserDTO) {
        PlatformUser platformUserExist = userService.lambdaQuery()
                .select(PlatformUser::getId)
                .eq(PlatformUser::getUsername, platformUserDTO.getUsername())
                .last("limit 1").one();
        if (null != platformUserExist) {
            throw new BusinessException("用户名已存在，请更换用户名");
        }
        platformUserExist = new PlatformUser();
        BeanUtils.copyProperties(platformUserDTO, platformUserExist);
        userService.save(platformUserExist);
        return ResponseWrapped.success();
    }
}
