package com.gaobug.auth.filter;

import com.gaobug.auth.service.CaptchaService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * 验证码校验过滤器
 */
@Component
public class CaptchaFilter implements Filter {
//    @Resource
    private CaptchaService captchaService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // TODO: 2022/5/31 登录频繁后需要进行登录校验
//        String randomId = servletRequest.getParameter("randomId");
//        Assert.notNull(randomId, "验证信息不能为空");
//        String captcha = servletRequest.getParameter("captcha");
//        Assert.notNull(captcha, "验证内容不能为空");
//        captchaService.verifyCaptcha(randomId, Integer.parseInt(captcha));
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
