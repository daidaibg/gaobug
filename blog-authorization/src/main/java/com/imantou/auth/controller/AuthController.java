package com.imantou.auth.controller;

import com.imantou.auth.service.LoginService;
import com.imantou.common.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController {

    @Resource
    private LoginService loginService;

    @GetMapping("/captcha/{randomId}")
    public ResponseWrapped<Object> captcha(@PathVariable String randomId) {
        return ResponseWrapped.success(loginService.getCaptcha(randomId));
    }

}
