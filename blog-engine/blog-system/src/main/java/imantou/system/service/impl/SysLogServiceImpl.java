package imantou.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import imantou.system.service.SysLogService;

import imantou.common.dao.sys.SysLogMapper;
import imantou.common.domain.SysLog;
import org.springframework.stereotype.Service;

/**
* @author gaobug
* @description 针对表【sys_log(系统日志)】的数据库操作Service实现
* @createDate 2022-04-27 11:37:24
*/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog>
    implements SysLogService {

}




