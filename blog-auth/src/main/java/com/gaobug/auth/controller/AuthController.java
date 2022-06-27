package com.gaobug.auth.controller;

import com.gaobug.auth.service.AuthService;
import com.gaobug.base.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 鉴权接口
 *
 * @author gaobug
 */
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
