package imantou.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import imantou.user.service.SysRoleService;

import imantou.common.dao.sys.SysRoleMapper;
import imantou.common.domain.SysRole;
import org.springframework.stereotype.Service;

/**
* @author gaobug
* @description 针对表【sys_role(角色)】的数据库操作Service实现
* @createDate 2022-04-27 11:37:24
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService {

}




