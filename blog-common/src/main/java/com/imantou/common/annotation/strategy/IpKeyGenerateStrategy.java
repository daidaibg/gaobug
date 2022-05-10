package com.imantou.common.annotation.strategy;

import com.imantou.common.annotation.metadata.MethodMetaData;
import com.imantou.common.utils.RequestUtils;

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
