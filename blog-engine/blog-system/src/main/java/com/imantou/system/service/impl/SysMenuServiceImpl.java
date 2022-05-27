package com.imantou.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imantou.system.service.SysMenuService;

import com.imantou.common.dao.sys.SysMenuMapper;
import com.imantou.common.domain.SysMenu;
import org.springframework.stereotype.Service;

/**
* @author gaobug
* @description 针对表【sys_menu(菜单管理)】的数据库操作Service实现
* @createDate 2022-04-27 11:37:24
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService {

}




