package com.gaobug.auth.controller;

import com.gaobug.api.dto.UserRegisterDTO;
import com.gaobug.auth.service.RegisterService;
import com.gaobug.base.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public ResponseWrapped<Object> registerUserByEmail(@RequestBody UserRegisterDTO form) {
        return ResponseWrapped.success(registerService.registerUserByEmail(form));
    }
}
