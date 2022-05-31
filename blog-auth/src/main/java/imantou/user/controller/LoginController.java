package imantou.user.controller;

import imantou.user.dto.LoginForm;
import imantou.user.service.LoginService;
import imantou.common.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户登录相关接口
 *
 * @author gaobug
 */
@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @GetMapping("/captcha/{randomId}")
    public ResponseWrapped<Object> captcha(@PathVariable String randomId) {
        return ResponseWrapped.success(loginService.getCaptcha(randomId));
    }

    /**
     * app登录
     */
    @PostMapping(value = "/app/login/{randomId}")
    public ResponseWrapped<Object> appLogin(@PathVariable String randomId, @RequestBody LoginForm form) {
        return ResponseWrapped.success(loginService.appUserLogin(randomId, form));
    }

    /**
     * 后台管理登录
     */
    @PostMapping(value = "/system/login/{randomId}")
    public ResponseWrapped<Object> systemLogin(@PathVariable String randomId, @RequestBody LoginForm form) {
        return ResponseWrapped.success(loginService.systemLogin(randomId, form));
    }
}
