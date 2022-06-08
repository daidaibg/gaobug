package com.imantou.security.adapter;

import java.util.List;

/**
 * 路径权限适配器接口
 *
 * @author gaobug
 */
public interface PathPatternsConfigAdapter {

    /**
     * 访问系统是否支持当前适配器
     *
     * @param appid 当前系统
     * @return 是否支持
     */
    default boolean supports(String appid) {
        return true;
    }

    /**
     * 不需要授权登陆的路径
     *
     * @return 不需要授权登陆的路径列表
     */
    List<String> getIgnorePathPatterns();

    /**
     * 需要授权登陆的路径
     *
     * @return 需要授权登陆的路径列表
     */
    List<String> getInterceptPathPatterns();

    /**
     * 当前路径是否放行
     * <p>
     * 默认不放行
     *
     * @param path 校验路径
     * @return 是否放行当前路径
     */
    default boolean isRelease(String path) {
        return false;
    }

    /**
     * 当前路径是否拦截
     * <p>
     * 默认拦截
     *
     * @param path 校验路径
     * @return 是否拦截当前路径
     */
    default boolean isIntercept(String path) {
        return true;
    }

}
