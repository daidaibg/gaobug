package com.imantou.auth.controller;

import com.imantou.auth.dto.LoginForm;
import com.imantou.auth.service.LoginService;
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

    //    @RateLimit(key = "'captcha'", strategy = {Strategy.DEFAULT, Strategy.IP}, count = 5)
//    @GetMapping("/captcha")
//    public void captcha(HttpServletResponse response) throws IOException {
//        // 设置请求头为输出图片类型
//        response.setContentType("image/gif");
//        response.setHeader("Pragma", "No-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//        // 三个参数分别为宽、高、位数
//        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
//        // 设置字体
//        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
//        // 设置类型，纯数字、纯字母、字母数字混合
//        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
//        // 验证码存入session
//        ShiroUtils.getSession().setAttribute("captcha", specCaptcha.text().toLowerCase());
//        // 输出图片流
//        specCaptcha.out(response.getOutputStream());
//    }
//    public static void main(String[] args) throws IOException {
//        Map<String, Object> resultMap = new HashMap<>();
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        org.springframework.core.io.Resource resource = resolver.getResource("classpath:static/image/test.jpeg");
//        ImageUtil.createImage(resource.getFile(), resultMap);
//        int xWidth = Integer.parseInt(resultMap.get("xWidth").toString());
//        log.info("验证值" + xWidth);
//        resultMap.remove("xWidth");
//    }

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
    public ResponseWrapped<Object> systemLogin(@RequestBody LoginForm form,@PathVariable String randomId) {
        return ResponseWrapped.success(loginService.systemLogin(form,randomId ));
    }
}
