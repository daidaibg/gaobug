package com.imantou.auth.controller;

import com.imantou.response.ResponseWrapped;
import com.imantou.auth.dto.PlatformRegisterDTO;
import com.imantou.auth.service.RegisterService;
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
@RequestMapping("/cs/user")
public class PlatformRegisterController {
    @Resource
    private RegisterService registerService;

    /**
     * 注册
     */
    @PostMapping(value = "/register")
    public ResponseWrapped<Object> registerUser(@RequestBody PlatformRegisterDTO form) {
        return registerService.registerUser(form);
    }
}
