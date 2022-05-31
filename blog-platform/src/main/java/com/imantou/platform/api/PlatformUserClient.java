package com.imantou.platform.api;

import com.imantou.advice.response.ResponseWrapped;
import com.imantou.api.user.PlatformUserService;
import com.imantou.api.vo.PlatformUserVO;
import com.imantou.platform.domain.PlatformUser;
import com.imantou.platform.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 后台管理用户api
 *
 * @author gaobug
 */
@RestController
public class PlatformUserClient implements PlatformUserService {
    @Resource
    private UserService userService;

    @Override
    public ResponseWrapped<PlatformUserVO> getUserByName(String userName) {
        PlatformUser platformUser = userService.lambdaQuery()
                .eq(PlatformUser::getUsername, userName)
                .last("limit 1").one();
        PlatformUserVO platformUserVO = new PlatformUserVO();
        BeanUtils.copyProperties(platformUser,platformUserVO);
        return new ResponseWrapped<>(platformUserVO);

    }
}
