package com.imantou.system.controller;

import com.imantou.system.service.SysUserService;
import imantou.common.domain.SysUser;
import imantou.common.remote.SysUserClientApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class SysUserController implements SysUserClientApi {

    @Resource
    private SysUserService userService;

    @Override
    @GetMapping(value = "getUserByName/{userName}")
    public SysUser getUserByName(@PathVariable String userName) {
        return userService.lambdaQuery().eq(SysUser::getUsername, userName).last("limit 1").one();
    }

    @Override
    @GetMapping(value = "getUserPermissions/{userId}")
    public Set<String> getUserPermissions(@PathVariable Integer userId) {
        return null;
    }
}
