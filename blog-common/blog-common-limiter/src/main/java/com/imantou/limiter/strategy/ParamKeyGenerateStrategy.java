package com.imantou.limiter.strategy;

import com.imantou.exception.metadata.MethodMetaData;
import lombok.AllArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.SortedMap;

/**
 * 指定参数
 *
 * @author hzq
 */
@AllArgsConstructor
public class ParamKeyGenerateStrategy implements IKeyGenerateStrategy {

    private final IKeyGenerateStrategy idempotencyKeyGenerateStrategy;

    @Override
    public Strategy getType() {
        return Strategy.PARAMETER;
    }

    @Override
    public String getKey(MethodMetaData methodMetadata, String parseKey) {
        StringBuilder key = new StringBuilder();
        String[] keys = methodMetadata.getKeys();
        if (ObjectUtils.isEmpty(keys)) {
            return idempotencyKeyGenerateStrategy.getKey(methodMetadata, parseKey);
        }
        SortedMap<String, String> argsMap = methodMetadata.getArgsMap();
        if (!ObjectUtils.isEmpty(argsMap)) {
            for (String parameterName : keys) {
                Object value = argsMap.get(parameterName);
                if (!ObjectUtils.isEmpty(value)) {
                    if (key.length() > 0) {
                        key.append(":");
                    }
                    key.append(value);
                }
            }
        }
        return key.toString();
    }
}
