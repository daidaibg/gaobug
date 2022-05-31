package imantou.common.verify.strategy;

import com.imantou.metadata.MethodMetaData;

/**
 * 参数校验接口
 *
 * @author hzq
 * @date 2021/11/19
 */
public interface IVerifyStrategy {

    /**
     * 参数校验
     *
     * @param methodMetadata 方法相关数据
     * @throws IllegalAccessException 执行异常
     * @throws InstantiationException 反射对象创建异常
     */
    void doCheck(MethodMetaData methodMetadata) throws IllegalAccessException, InstantiationException;

}
