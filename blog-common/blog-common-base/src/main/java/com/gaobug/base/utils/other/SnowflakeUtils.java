package com.gaobug.base.utils.other;

import cn.hutool.core.lang.generator.Generator;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.gaobug.base.utils.spring.SpringContextUtils;


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

    public static String nextStr(String system) {
        return String.join("-", system, snowflakeGenerator.next().toString());
    }
}
