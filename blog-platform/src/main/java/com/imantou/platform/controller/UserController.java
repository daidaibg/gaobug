package com.imantou.platform.controller;

import imantou.app.service.UserService;
import imantou.common.domain.User;
import imantou.common.response.ResponseWrapped;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping(value = "getUserByName/{userName}")
    public ResponseWrapped<Object> getUserByName(@PathVariable String userName) {
        return ResponseWrapped.success(userService.lambdaQuery().eq(User::getUsername, userName).last("limit 1").one());
    }
}
