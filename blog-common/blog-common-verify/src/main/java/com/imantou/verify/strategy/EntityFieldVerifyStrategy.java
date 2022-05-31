package com.imantou.verify.strategy;


import com.imantou.exception.metadata.MethodMetaData;
import com.imantou.exception.utils.ParseDataUtils;
import com.imantou.verify.Verify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 参数校验-实体字段校验
 *
 * @author hzq
 * @date 2021/11/22
 */
@Slf4j
public class EntityFieldVerifyStrategy extends AbstractVerifyStrategy {

    @Override
    public void doCheck(MethodMetaData methodMetadata) throws IllegalAccessException, InstantiationException {
        Object[] args = methodMetadata.getArgs();
        // 实体参数校验
        for (Object bean : args) {
            if (bean == null) {
                continue;
            }
            this.selectVerifyRule(bean);
        }
    }

    /**
     * 根据数据类型选择校验规则
     *
     * @param object object对象
     * @throws IllegalAccessException 执行异常
     * @throws InstantiationException 反射对象创建异常
     */
    private void selectVerifyRule(Object object) throws IllegalAccessException, InstantiationException {
        Field[] fields = object.getClass().getDeclaredFields();
        if (!ObjectUtils.isEmpty(fields)) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Verify.class)) {
                    this.selectVerifyRule(object, field);
                }
            }
        }
    }

    /**
     * 根据数据类型选择校验规则
     *
     * @param object object对象
     * @param field  字段
     * @throws IllegalAccessException 执行异常
     * @throws InstantiationException 反射对象创建异常
     */
    private void selectVerifyRule(Object object, Field field) throws IllegalAccessException, InstantiationException {
        //在Java反射机制中，通过GetDeclaredConstructor获取的私有构造进行暴力访问
        field.setAccessible(true);
        Verify verify = field.getAnnotation(Verify.class);
        Class<?> paramType = field.getType();
        String explain = StringUtils.hasLength(verify.value()) ? verify.value() : field.getName();
        Object objValue = field.get(object);
        if (verify.nested()) {
            // 嵌套对象的校验逻辑
            // 集合对象校验
            if (Collection.class.isAssignableFrom(paramType)) {
                if (ObjectUtils.isEmpty(objValue)) {
                    // 自身校验必填
                    this.doRequired(verify.required(), explain, verify.errorMsg(), null);
                } else {
                    Collection<?> collection = (Collection<?>) objValue;
                    for (Object o : collection) {
                        // 递归重新查找校验方式
                        this.selectVerifyRule(o);
                    }
                }
            } else {
                // Object对象校验
                // 递归校验嵌套的对象可能存在null的情况，使用反射创建对象
                if (objValue == null) {
                    objValue = paramType.newInstance();
                }
                // 嵌套对象递归校验
                this.selectVerifyRule(objValue);
            }
        } else {
            // 基本数据类型、时间、Map校验
            // 执行校验
            this.doCheck(verify, explain, verify.errorMsg(), objValue);
            // 默认值
            if (StringUtils.hasLength(verify.defaultValue()) && ObjectUtils.isEmpty(objValue)) {
                field.set(object, ParseDataUtils.parseValue(paramType, verify.defaultValue()));
            } else {
                // 替换枚举值
                if (!StringUtils.isEmpty(objValue) && !ObjectUtils.isEmpty(verify.replace())) {
                    String replaceAfter = this.doReplace(verify.replace(), objValue);
                    field.set(object, ParseDataUtils.parseValue(paramType, replaceAfter));
                }
            }
        }
    }
}
