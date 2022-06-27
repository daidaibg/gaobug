package com.gaobug.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gaobug.platform.domain.PlatformUser;

/**
* @author huang
* @description 针对表【t_user(用户表)】的数据库操作Mapper
* @createDate 2022-05-30 14:00:27
* @Entity com.gaobug.common.domain.TUser
*/
public interface UserMapper extends BaseMapper<PlatformUser> {

    PlatformUser selectUserByAccountOrEmail(String account);
}




