package com.gaobug.auth.controller;

import com.gaobug.auth.dto.LoginForm;
import com.gaobug.auth.dto.PlatformLoginForm;
import com.gaobug.auth.service.LoginService;
import com.gaobug.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * 平台用户登录相关接口
 *
 * @author gaobug
 */
@Slf4j
@RestController
@RequestMapping("/auth/cs/user")
public class PlatformLoginController {
    @Resource
    private LoginService loginService;

    /**
     * 登录
     */
    @PostMapping(value = "/login")
    public ResponseWrapped<Object> platformLogin(@RequestBody PlatformLoginForm form) {
        return ResponseWrapped.success(loginService.platformUserLogin(form));
    }
    /**
     * 获取登录用户信息
     */
    @GetMapping("/info")
    public ResponseWrapped<Object> getLoginUserInfo() throws ExecutionException, InterruptedException {
        return ResponseWrapped.success(loginService.getPlatformLoginUserInfo());
    }

    /**
     * 退出登录
     */
    @PostMapping("/loginOut")
    public ResponseWrapped<Object> loginOut() {
        return ResponseWrapped.success();
    }
}
