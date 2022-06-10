package com.gaobug.base.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 雪花生成器配置文件
 *
 * @author hzq
 * @date 2022/4/21 9:58
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = SnowflakeProperties.PREFIX)
public class SnowflakeProperties {
    public final static String PREFIX = "blog.snowflake";

    private Long workerId;

    private Long dataCenterId;
}
