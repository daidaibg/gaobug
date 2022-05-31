package imantou.common.verify.handler;


import imantou.common.verify.strategy.IVerifyStrategy;
import com.imantou.handler.IProceedHandler;
import com.imantou.metadata.MethodMetaData;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;

/**
 * 参数校验handler
 *
 * @author hzq
 * @date 2021/11/24
 */
@AllArgsConstructor
public class VerifyHandler implements IProceedHandler {

    private List<IVerifyStrategy> verifyStrategyList;

    @Override
    public Object proceed(ProceedingJoinPoint pjp, MethodMetaData methodMetadata) throws Throwable {
        for (IVerifyStrategy verify : verifyStrategyList) {
            verify.doCheck(methodMetadata);
        }
       return pjp.proceed();
    }
}
