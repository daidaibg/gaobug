package com.gaobug.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gaobug.platform.dao.UserMapper;
import com.gaobug.platform.domain.PlatformUser;
import com.gaobug.platform.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang
 * @description 针对表【t_user(用户表)】的数据库操作Service实现
 * @createDate 2022-05-30 14:00:27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, PlatformUser> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public PlatformUser getUserByAccountOrEmail(String account) {
        return userMapper.selectUserByAccountOrEmail(account);
    }
}




