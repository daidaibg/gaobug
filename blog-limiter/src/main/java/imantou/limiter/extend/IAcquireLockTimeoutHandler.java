package imantou.limiter.extend;

import imantou.limiter.metadata.DistributedLockMethodMetaData;
import com.imantou.metadata.MethodMetaData;


/**
 * 分布式锁获取超时后自定义处理策略
 *
 * @author hzq
 */
public interface IAcquireLockTimeoutHandler {
    /**
     * 判断是否支持当前处理策略
     *
     * @param methodMetadata 当前运行时参数 {@link DistributedLockMethodMetaData}
     * @return 是否支持
     */
    boolean supports(MethodMetaData methodMetadata);

    /**
     * 在分布式锁获取超时失败时的处理策略
     *
     * @param methodMetadata 当前运行时参数 {@link DistributedLockMethodMetaData}
     * @return 返回结果
     */
    Object onDistributedLockFailure(MethodMetaData methodMetadata);
}
