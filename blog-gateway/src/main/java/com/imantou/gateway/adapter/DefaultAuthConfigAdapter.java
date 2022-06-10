package com.imantou.gateway.adapter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * @author gaobug
 */

@Getter
@Setter
@ToString
@RefreshScope
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = DefaultAuthConfigAdapter.PREFIX)
public class DefaultAuthConfigAdapter implements PathPatternsConfigAdapter {
    /**
     * 配置前缀
     */
    public static final String PREFIX = "gaobug.security.request.default";
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    /**
     * 支持的应用列表
     */
    private List<String> supportsAppIds;
    /**
     * 白名单
     */
    private List<String> ignorePathPatterns;
    /**
     * 拦截路径
     */
    private List<String> interceptPathPatterns = Collections.singletonList("/*");

    @Override
    public boolean supports(String appid) {
        if (!ObjectUtils.isEmpty(supportsAppIds)) {
            for (String supportsAppid : supportsAppIds) {
                if (Objects.equals(appid, supportsAppid)) {
                    return true;
                }
            }
        }
        return PathPatternsConfigAdapter.super.supports(appid);
    }

    @Override
    public boolean isRelease(String path) {
        if (!ObjectUtils.isEmpty(ignorePathPatterns)) {
            for (String ignorePath : ignorePathPatterns) {
                if (PATH_MATCHER.match(ignorePath, path)) {
                    return true;
                }
            }
        }
        if (!ObjectUtils.isEmpty(interceptPathPatterns)) {
            for (String interceptPath : interceptPathPatterns) {
                if (PATH_MATCHER.match(interceptPath, path)) {
                    return false;
                }
            }
        }
        return PathPatternsConfigAdapter.super.isRelease(path);
    }

    @Override
    public boolean isIntercept(String path) {
        return !this.isRelease(path);
    }
}
