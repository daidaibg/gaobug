package com.imantou.limiter.strategy;

import com.imantou.base.metadata.MethodMetaData;
import org.springframework.util.StringUtils;


/**
 * 默认策略
 * 方法名称
 *
 * @author hzq
 */
public class DefaultKeyGenerateStrategy implements IKeyGenerateStrategy {

    @Override
    public Strategy getType() {
        return Strategy.DEFAULT;
    }

    @Override
    public String getKey(MethodMetaData methodMetadata, String parseKey) {
        if (StringUtils.hasLength(parseKey)) {
            return parseKey;
        }
        return methodMetadata.getClassMethodName();
    }
}