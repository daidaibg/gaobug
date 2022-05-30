package com.imantou.auth.controller;

import com.imantou.auth.service.AuthService;
import com.imantou.common.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController {
    @Resource
    private AuthService authService;

    /**
     * 鉴权
     */
    @GetMapping("/authorization")
    public ResponseWrapped<Object> authorization() {
        return ResponseWrapped.success(authService.authorization());
    }

}
