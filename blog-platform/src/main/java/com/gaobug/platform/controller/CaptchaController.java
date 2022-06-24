package com.gaobug.platform.controller;

import com.gaobug.base.cache.constant.CaptchaCode;
import com.gaobug.base.response.ResponseWrapped;
import com.gaobug.base.utils.redis.RedisUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 博客分类接口
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 获取博客列表
     */
    @GetMapping("/email")
    public ResponseWrapped<Object> send(String email) {
        //邮件设置
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("电子邮箱验证码");
        String verificationCode = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        message.setText("电子邮箱验证码，您正在注册搞bug平台账号，验证码是：" + verificationCode + "，10分钟内有效，请及时输入。");
        message.setTo(email);
        message.setBcc();
        message.setFrom("gaobug@gaobug.com");
        javaMailSender.send(message);
        // 十分钟过期
        RedisUtil.set(CaptchaCode.CAPTCHA_EMAIL_REGISTER + email, verificationCode, 60_0_000L);
        return ResponseWrapped.success();
    }

}
