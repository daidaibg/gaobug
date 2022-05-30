package com.imantou.auth.controller;

import com.imantou.auth.service.AuthService;
import com.imantou.common.response.ResponseWrapped;
import com.imantou.common.vo.JwtTokenVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author gaobug
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AuthService authService;

    @GetMapping("/authorization")
    public ResponseWrapped<JwtTokenVO> authorization() {
        return authService.authorization();
    }

}