package imantou.limiter.exception;

/**
 * 限流异常
 *
 * @author hzq
 */
public class RateLimitException extends RuntimeException {

    public RateLimitException(String message) {
        super(message);
    }
}
