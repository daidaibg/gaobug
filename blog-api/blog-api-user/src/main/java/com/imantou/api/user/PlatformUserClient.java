package com.imantou.api.user;


import com.imantou.api.dto.PlatformRegisterDTO;
import com.imantou.response.ResponseWrapped;
import com.imantou.api.dto.PlatformUserDTO;
import com.imantou.api.vo.PlatformUserVO;
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
     * @param userName 用户名
     * @return 用户信息
     */
    @GetMapping(value = "/platform/user/getUserByName/{userName}")
    PlatformUserVO getUserByName(@PathVariable String userName);

    /**
     * 保存注册新用户
     *
     * @param platformUserDTO 用户信息表单
     * @return 注册结果
     */
    @PostMapping(value = "/platform/user/registerUser")
    ResponseWrapped<Object> registerUser(@RequestBody PlatformRegisterDTO platformUserDTO);
}
