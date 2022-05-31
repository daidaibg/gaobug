package imantou.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import imantou.system.service.SysRoleMenuService;

import imantou.common.dao.sys.SysRoleMenuMapper;
import imantou.common.domain.SysRoleMenu;
import org.springframework.stereotype.Service;

/**
* @author gaobug
* @description 针对表【sys_role_menu(角色与菜单对应关系)】的数据库操作Service实现
* @createDate 2022-04-27 11:37:24
*/
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
    implements SysRoleMenuService {

}




