package com.gaobug.auth.controller;


import com.gaobug.auth.service.CaptchaService;
import com.gaobug.base.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户登录相关接口
 *
 * @author gaobug
 */
@Slf4j
@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    @Resource
    private CaptchaService captchaService;

    @GetMapping
    public ResponseWrapped<Object> captcha() {
        return ResponseWrapped.success(captchaService.getCaptcha());
    }
}
