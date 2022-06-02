package com.imantou.auth.controller;

import com.imantou.api.dto.PlatformRegisterDTO;
import com.imantou.auth.service.RegisterService;
import com.imantou.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 平台用户登录相关接口
 *
 * @author gaobug
 */
@Slf4j
@RestController
@RequestMapping("/auth/cs/user")
public class PlatformRegisterController {
    @Resource
    private RegisterService registerService;

    /**
     * 注册
     */
    @PostMapping(value = "/register")
    public ResponseWrapped<Object> registerUser(@Valid @RequestBody PlatformRegisterDTO form) {
        return registerService.registerUser(form);
    }
}
