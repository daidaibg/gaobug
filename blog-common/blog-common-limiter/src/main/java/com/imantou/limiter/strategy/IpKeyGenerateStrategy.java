package com.imantou.limiter.strategy;

import com.imantou.exception.metadata.MethodMetaData;
import com.imantou.exception.utils.RequestUtils;

/**
 * ip策略
 *
 * @author hzq
 */
public class IpKeyGenerateStrategy implements IKeyGenerateStrategy {

    @Override
    public Strategy getType() {
        return Strategy.IP;
    }

    @Override
    public String getKey(MethodMetaData methodMetadata, String parseKey) {
        return RequestUtils.getIp(this.getRequest());
    }

}
