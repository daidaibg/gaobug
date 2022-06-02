package com.imantou.api.dto;

/**
 * 平台用户注册表单
 *
 * @author gaobug
 */

public interface PlatformRegister {

    default String getPassword() {
        return null;
    }

    default void setPassword(String password) {

    }

    default String getEmail() {
        return null;
    }

    default void setEmail(String email) {
    }

    default String getCode() {
        return null;
    }

    default void setCode(String code) {
    }

    default String getMobile() {
        return null;
    }

    default void setMobile(String mobile) {
    }
}
