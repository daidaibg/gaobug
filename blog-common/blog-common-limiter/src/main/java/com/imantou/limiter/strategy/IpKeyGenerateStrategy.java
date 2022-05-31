package com.imantou.limiter.strategy;

import com.imantou.base.metadata.MethodMetaData;
import com.imantou.base.utils.RequestUtils;

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
