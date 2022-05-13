package com.imantou.auth.controller;

import com.imantou.auth.service.AuthService;
import com.imantou.common.domain.JwtTokenVO;
import com.imantou.common.domain.LoginForm;
import com.imantou.common.remote.AuthClientApi;
import com.imantou.common.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private AuthService authService;

    @GetMapping("/captcha/{randomId}")
    public ResponseWrapped<Object> getCaptcha(@PathVariable String randomId) {
        return authService.getCaptcha(randomId);
    }

    @PostMapping(value = "/app/login")
    public ResponseWrapped<Object> appLogin(@RequestBody LoginForm form) {
        return ResponseWrapped.success(form);
    }

    @PostMapping(value = "/system/login/{randomId}")
    public ResponseWrapped<Object> systemLogin(@RequestBody LoginForm form, @PathVariable String randomId) {
        return authService.systemLogin(form, randomId);
    }

}