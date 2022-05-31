package imantou.auth.controller;

import imantou.auth.api.AuthConsumerService;
import imantou.common.dto.LoginForm;
import imantou.common.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author gaobug
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginConsumerController {
    @Resource
    private AuthConsumerService authProviderService;

    @GetMapping("/captcha/{randomId}")
    public ResponseWrapped<Object> getCaptcha(@PathVariable String randomId) {
        return authProviderService.getCaptcha(randomId);
    }

    @GetMapping(value = "/app/info")
    public ResponseWrapped<Object> getAppLoginUserInfo() {
        return authProviderService.getAppLoginUserInfo();
    }

    @PostMapping(value = "/app/login/{randomId}")
    public ResponseWrapped<Object> appLogin(@RequestBody LoginForm form, @PathVariable String randomId) {
        return authProviderService.appUserLogin(form, randomId);
    }

    @PostMapping(value = "/system/login/{randomId}")
    public ResponseWrapped<Object> systemLogin(@RequestBody LoginForm form, @PathVariable String randomId) {
        return authProviderService.systemLogin(form, randomId);
    }

}