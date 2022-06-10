package com.gaobug.auth.controller;

import com.gaobug.response.ResponseWrapped;
import com.gaobug.auth.dto.LoginForm;
import com.gaobug.auth.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 后台用户登录相关接口
 *
 * @author gaobug
 */
@Slf4j
@RestController
@RequestMapping("/login/system")
public class SystemLoginController {
    @Resource
    private LoginService loginService;

    /**
     * 后台管理登录
     */
    @PostMapping(value = "/login/{randomId}")
    public ResponseWrapped<Object> systemLogin(@PathVariable String randomId, @RequestBody LoginForm form) {
        return ResponseWrapped.success(loginService.systemLogin(randomId, form));
    }

    /**
     * 获取登录用户信息
     */
    @GetMapping("/info")
    public ResponseWrapped<Object> getLoginUserInfo() {
        return ResponseWrapped.success();
    }

    /**
     * 退出登录
     */
    @PostMapping("/loginOut")
    public ResponseWrapped<Object> loginOut() {
        return ResponseWrapped.success();
    }
}
