package imantou.limiter.exception;

/**
 * 分布式锁异常
 *
 * @author hzq
 */
public class DistributedLockException extends RuntimeException {

    public DistributedLockException(Throwable cause) {
        super(cause);
    }

    public DistributedLockException(String message) {
        super(message);
    }

    public DistributedLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
