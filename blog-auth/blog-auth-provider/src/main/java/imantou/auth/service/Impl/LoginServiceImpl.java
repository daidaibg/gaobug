package imantou.auth.service.Impl;

import cn.hutool.core.lang.generator.Generator;
import imantou.auth.api.AppUserService;
import imantou.auth.dto.LoginForm;
import imantou.auth.service.LoginService;
import imantou.auth.api.SysUserService;
import imantou.common.vo.AuthTokenVO;
import imantou.common.domain.SysUser;
import imantou.common.vo.UserContext;
import imantou.common.enums.ResultEnum;
import imantou.common.exception.BusinessException;
import imantou.common.exception.CaptchaException;
import com.imantou.utils.EncryptUtils;
import com.imantou.utils.ImageUtil;
import imantou.common.utils.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 登录服务
 *
 * @author gaobug
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    /**
     * 滑动验证码允许偏差值
     */
    private static final Integer DEVIATION = 15;
    @Resource
    private SysUserService userService;
    @Resource
    private AppUserService appUserService;
    @Resource
    private RedisHelper redisHelper;
    @Resource
    private Generator<Long> snowflakeGenerator;

    @Override
    public Map<String, Object> getCaptcha(String randomId) {
        Map<String, Object> resultMap = ImageUtil.getCaptcha();
        resultMap.put("randomId", randomId);
        redisHelper.set(randomId, resultMap.get("xWidth"), 20000L, TimeUnit.MILLISECONDS);
        resultMap.remove("xWidth");
        return resultMap;
    }

    @Override
    public Boolean verifyCaptcha(String randomId, Integer captchaCode) {
        if (null == captchaCode) {
            throw new CaptchaException(ResultEnum.LOGON_CAPTCHA_FAILED);
        }
        Object objCaptcha = redisHelper.get(randomId);
        if (Objects.isNull(objCaptcha)) {
            throw new CaptchaException(ResultEnum.LOGON_CAPTCHA_EXPIRE);
        }
        // 验证滑动验证码滑动距离 偏差值允许在15
        final int captcha = Integer.parseInt(objCaptcha.toString());
        if (captcha - DEVIATION >= captchaCode || captcha + DEVIATION <= captchaCode) {
            throw new CaptchaException(ResultEnum.LOGON_CAPTCHA_FAILED);
        }
        return true;
    }

    @Override
    public AuthTokenVO appUserLogin(String randomId, LoginForm form) {
        this.verifyCaptcha(randomId, form.getCaptcha());
        return null;
    }

    @Override
    public AuthTokenVO systemLogin(String randomId, LoginForm form) {
        this.verifyCaptcha(randomId, form.getCaptcha());
        SysUser user = userService.getUserByName(form.getUsername());
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(EncryptUtils.sha256(form.getPassword(), user.getSalt()))) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }
        // 缓存用户信息
        UserContext userContext = new UserContext();
        BeanUtils.copyProperties(user, userContext);
        String token = snowflakeGenerator.next().toString();
        redisHelper.set(token, userContext);
        return new AuthTokenVO(token);
    }
}
