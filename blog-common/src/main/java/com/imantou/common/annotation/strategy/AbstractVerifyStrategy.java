package com.imantou.common.annotation.strategy;

import com.imantou.common.annotation.Verify;
import com.imantou.common.exception.ParamsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 参数校验抽象类
 *
 * @author hzq
 * @date 2021/11/19
 */
@Slf4j
public abstract class AbstractVerifyStrategy implements IVerifyStrategy {

    /**
     * 执行校验
     *
     * @param verify   注解
     * @param explain  字段描述
     * @param errorMsg 错误消息
     * @param objValue    数据
     */
    protected void doCheck(Verify verify, String explain, String errorMsg, Object objValue) {
        // 校验必填
        this.doRequired(verify.required(), explain, errorMsg, objValue);
        // 校验正则
        this.doRegex(verify.regex(), explain, errorMsg, objValue);
        // 校验最小长度
        this.doMinLength(verify.minLength(), explain, errorMsg, objValue);
        // 校验最大长度
        this.doMaxLength(verify.maxLength(), explain, errorMsg, objValue);
    }


    /**
     * 字段必填校验
     *
     * @param required 是否必填
     * @param explain  字段描述
     * @param errorMsg 错误提示消息
     * @param objValue    校验的值
     */
    protected void doRequired(boolean required, String explain, String errorMsg, Object objValue) {
        if (required && ObjectUtils.isEmpty(objValue)) {
            throw new ParamsException("".equals(errorMsg) ? explain + "不能为空！" : errorMsg);
        }
    }

    /**
     * 正则校验
     *
     * @param regex    正则
     * @param explain  字段描述
     * @param errorMsg 错误提示消息
     * @param objValue    校验的值
     */
    protected void doRegex(String regex, String explain, String errorMsg, Object objValue) {
        if (StringUtils.hasText(regex)) {
            errorMsg = StringUtils.hasText(errorMsg) ? errorMsg : explain + "格式不正确！";
            if (ObjectUtils.isEmpty(objValue)) {
                throw new ParamsException(errorMsg);
            }
            Class<?> paramType = objValue.getClass();
            if (CharSequence.class.isAssignableFrom(paramType) || Number.class.isAssignableFrom(paramType)) {
                Pattern pattern = Pattern.compile(regex);
                if (!pattern.matcher(objValue.toString()).matches()) {
                    throw new ParamsException(errorMsg);
                }
            } else {
                log.error("@Verify.regex is not supported " + paramType.getName());
            }
        }
    }

    /**
     * 最小长度校验
     *
     * @param minLength 限制最小长度
     * @param explain   字段描述
     * @param errorMsg  错误提示消息
     * @param objValue     校验的值
     */
    protected void doMinLength(int minLength, String explain, String errorMsg, Object objValue) {
        if (minLength > 0) {
            errorMsg = StringUtils.hasText(errorMsg) ? errorMsg : explain + "过短！";
            if (ObjectUtils.isEmpty(objValue)) {
                throw new ParamsException(errorMsg);
            }
            Class<?> paramType = objValue.getClass();
            if (CharSequence.class.isAssignableFrom(paramType) || Number.class.isAssignableFrom(paramType)) {
                //最小长度校验
                if (objValue.toString().length() < minLength) {
                    throw new ParamsException(errorMsg);
                }
            } else {
                log.error("@Verify.minLength is not supported " + paramType.getName());
            }
        }
    }

    /**
     * 最大长度校验
     *
     * @param maxLength 限制最大长度
     * @param explain   字段描述
     * @param errorMsg  错误提示消息
     * @param objValue     校验的值
     */
    protected void doMaxLength(int maxLength, String explain, String errorMsg, Object objValue) {
        if (maxLength > 0) {
            errorMsg = StringUtils.hasText(errorMsg) ? errorMsg : explain + "过长！";
            if (ObjectUtils.isEmpty(objValue)) {
                throw new ParamsException(errorMsg);
            }
            Class<?> paramType = objValue.getClass();
            if (CharSequence.class.isAssignableFrom(paramType) || Number.class.isAssignableFrom(paramType)) {
                //最大长度校验
                if (objValue.toString().length() > maxLength) {
                    throw new ParamsException(errorMsg);
                }
            } else {
                log.error("@Verify.maxLength is not supported " + paramType.getName());
            }
        }
    }


    /**
     * 处理枚举值
     *
     * @param replaceArray 替换的数据数组
     * @param objValue     匹配的值
     * @return 替换后的枚举值
     */
    protected String doReplace(String[] replaceArray, Object objValue) {
        String[] temp;
        String strValue = String.valueOf(objValue);
        for (String str : replaceArray) {
            temp = str.split("_");
            if (strValue.equals(temp[0])) {
                strValue = temp[1];
                break;
            }
        }
        return strValue;
    }
}
