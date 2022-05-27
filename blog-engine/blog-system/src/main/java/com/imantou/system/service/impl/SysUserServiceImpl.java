package com.imantou.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imantou.system.service.SysUserService;

import com.imantou.common.dao.sys.SysUserMapper;
import com.imantou.common.domain.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gaobug
 * @description 针对表【sys_user(系统用户)】的数据库操作Service实现
 * @createDate 2022-04-27 11:37:24
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Resource
    private SysUserMapper userMapper;

}




