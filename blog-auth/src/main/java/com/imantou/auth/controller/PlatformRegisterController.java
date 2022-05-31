package com.imantou.auth.controller;

import com.imantou.advice.response.ResponseWrapped;
import com.imantou.auth.dto.LoginForm;
import com.imantou.auth.dto.RegisterForm;
import com.imantou.auth.service.LoginService;
import com.imantou.auth.service.RegisterService;
import com.imantou.auth.vo.PlatformUserContextVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 平台用户登录相关接口
 *
 * @author gaobug
 */
@Slf4j
@RestController
@RequestMapping("/register/platform")
public class PlatformRegisterController {
    @Resource
    private RegisterService registerService;

    /**
     * 登录
     */
    @PostMapping(value = "/login")
    public ResponseWrapped<Object> registerUser(@RequestBody RegisterForm form) {
        return ResponseWrapped.success(registerService.registerUser(form));
    }
}
