package com.imantou.common.annotation.context;

import com.imantou.common.annotation.distributedlock.IDistributedLockTemplate;
import com.imantou.common.annotation.extend.IAcquireLockTimeoutHandler;
import com.imantou.common.annotation.metadata.MethodMetaData;
import com.imantou.common.annotation.extend.IDistributedLockListener;
import lombok.AllArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 分布式锁容器
 *
 * @author hzq
 */
@AllArgsConstructor
public class DefaultDistributedContext implements DistributedContext {
    private IDistributedLockTemplate distributedLockTemplate;
    private List<IAcquireLockTimeoutHandler> acquireLockTimeoutHandlers;
    private List<IDistributedLockListener> distributedLimitListeners;


    @Override
    public IDistributedLockTemplate getDistributedLockTemplate() {
        return distributedLockTemplate;
    }

    @Override
    public List<IAcquireLockTimeoutHandler> getAcquireLockTimeoutHandlers() {
        List<IAcquireLockTimeoutHandler> result = new ArrayList<>();
        if (!ObjectUtils.isEmpty(acquireLockTimeoutHandlers)) {
            result.addAll(acquireLockTimeoutHandlers);
        }
        return result;
    }

    @Override
    public IAcquireLockTimeoutHandler getAcquireLockTimeoutHandler(MethodMetaData methodMetadata) {
        Assert.notNull(methodMetadata,"'methodMetadata' cannot be null");
        if (!ObjectUtils.isEmpty(acquireLockTimeoutHandlers)) {
            return acquireLockTimeoutHandlers.stream()
                    .filter(acquireLockTimeoutHandler -> acquireLockTimeoutHandler.supports(methodMetadata))
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public List<IDistributedLockListener> getDistributedLimitListeners() {
        List<IDistributedLockListener> result = new ArrayList<>();
        if (!ObjectUtils.isEmpty(distributedLimitListeners)) {
            result.addAll(distributedLimitListeners);
        }
        return result;
    }

    @Override
    public List<IDistributedLockListener> getDistributedLimitListeners(MethodMetaData methodMetadata) {
        Assert.notNull(methodMetadata,"'methodMetadata' cannot be null");
        List<IDistributedLockListener> result = new ArrayList<>();
        if (!ObjectUtils.isEmpty(distributedLimitListeners)) {
            distributedLimitListeners.forEach(distributedLimitListener->{
                if (distributedLimitListener.supports(methodMetadata)) {
                    result.add(distributedLimitListener);
                }
            });
        }
        return result;
    }


    @PostConstruct
    public void init() {
        if (!ObjectUtils.isEmpty(acquireLockTimeoutHandlers)) {
            this.acquireLockTimeoutHandlers = Collections.unmodifiableList(acquireLockTimeoutHandlers);
        }
        if (!ObjectUtils.isEmpty(distributedLimitListeners)) {
            this.distributedLimitListeners = Collections.unmodifiableList(distributedLimitListeners);
        }
    }
}
