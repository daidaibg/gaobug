package com.gaobug.api.user;


import com.gaobug.api.dto.UserRegisterDTO;
import com.gaobug.api.vo.PlatformUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 后台管理用户api
 *
 * @author gaobug
 */
@FeignClient(value = "blog-platform", contextId = "platform-user")
public interface PlatformUserClient {
    /**
     * 根据用户名获取用户信息
     *
     * @param account 用户名或邮箱
     * @return 用户信息
     */
    @GetMapping(value = "/platform/user/getUserByName/{account}")
    PlatformUserVO getUserByAccountOrEmail(@PathVariable String account);

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    @GetMapping(value = "/platform/user/getUserByMobile/{mobile}")
    PlatformUserVO getUserByMobile(@PathVariable String mobile);

    /**
     * 保存注册新用户
     *
     * @param platformRegister 用户信息表单
     * @return 注册结果
     */
    @PostMapping(value = "/platform/user/register")
    PlatformUserVO saveRegisterUser(@RequestBody UserRegisterDTO platformRegister);
}
