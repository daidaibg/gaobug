package com.imantou.auth.controller;

import com.imantou.auth.dto.LoginForm;
import com.imantou.auth.service.LoginService;
import com.imantou.common.annotation.RateLimit;
import com.imantou.common.annotation.strategy.Strategy;
import com.imantou.common.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @RateLimit(key = "'captcha'", strategy = {Strategy.DEFAULT, Strategy.IP}, count = 3)
    @GetMapping("/captcha/{randomId}")
    public ResponseWrapped<Object> captcha(@PathVariable String randomId) {
        return ResponseWrapped.success(loginService.getCaptcha(randomId));
    }

    /**
     * app登录
     */
    @PostMapping(value = "/app/login")
    public ResponseWrapped<Object> appLogin(@RequestBody LoginForm form) {
        return ResponseWrapped.success(form);
    }

    /**
     * 后台管理登录
     */
    @PostMapping(value = "/system/login/{randomId}")
    public ResponseWrapped<Object> systemLogin(@PathVariable String randomId, @RequestBody LoginForm form) {
        return ResponseWrapped.success(loginService.systemLogin(randomId, form));
    }
}
