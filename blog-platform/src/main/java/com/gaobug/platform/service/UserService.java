package com.gaobug.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaobug.platform.domain.PlatformUser;

/**
 * @author huang
 * @description 针对表【t_user(用户表)】的数据库操作Service
 * @createDate 2022-05-30 14:00:27
 */
public interface UserService extends IService<PlatformUser> {
    /**
     * 根据用户名或邮箱获取用户信息
     *
     * @param account 账号信息
     * @return 用户信息
     */
    PlatformUser getUserByAccountOrEmail(String account);
}
