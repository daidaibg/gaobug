package com.imantou.base.utils;

import cn.hutool.core.lang.generator.Generator;
import cn.hutool.core.lang.generator.SnowflakeGenerator;

/**
 * 雪花id生成工具类
 */
public class SnowflakeUtils {

    private static final Generator<Long> snowflakeGenerator = SpringContextUtils.getBean("snowflakeGenerator",
            SnowflakeGenerator.class);

    public static Long next() {
        return snowflakeGenerator.next();
    }

    public static String nextStr() {
        return snowflakeGenerator.next().toString();
    }
}
