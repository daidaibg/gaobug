package imantou.common.verify.exception;

/**
 * 参数校验异常
 *
 * @author hzq
 * @date 2021/11/22
 */
public class ParamsException extends RuntimeException {

    public ParamsException(String message) {
        super(message);
    }
}
