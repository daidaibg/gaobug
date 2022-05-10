package com.imantou.common.utils;


import com.imantou.common.domain.UserContext;
import com.imantou.common.exception.BusinessException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 */
public class ShiroUtils {
    /**
     * 加密算法
     */
    public final static String hashAlgorithmName = "SHA-256";
    /**
     * 循环次数
     */
    public final static int hashIterations = 16;

    public final static String CAPTCHA = "captcha";

    public static String sha256(String password, String salt) {
        return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static UserContext getLoginUser() {
        return (UserContext) SecurityUtils.getSubject().getPrincipal();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }


    public static void setCaptcha(Integer captchaCode) {
        ShiroUtils.setSessionAttribute(CAPTCHA, captchaCode);
    }

    public static int getCaptcha() {
        Object captcha = getSessionAttribute(CAPTCHA);
        if (null == captcha) {
            throw new BusinessException("验证已失效");
        }
        getSession().removeAttribute(CAPTCHA);
        return Integer.parseInt(captcha.toString());
    }
}
