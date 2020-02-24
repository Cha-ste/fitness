package com.ocean.aop;

import com.ocean.vo.CodeMsg;
import com.ocean.vo.ResultBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class ResultBeanAop {
    private static Logger logger = LoggerFactory.getLogger(ResultBeanAop.class);

    @Pointcut("execution(public com.ocean.vo.ResultBean *(..))")
    public void performance() {}

    @Around("performance()")
    public Object handlerControllerMethod (ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        ResultBean<?> resultBean;
        try {
            resultBean = (ResultBean<?>) pjp.proceed();
            logger.info(pjp.getSignature() + ", elapsed time: " + (System.currentTimeMillis() - startTime) + "ms");
        } catch (Throwable e) {
            resultBean = handlerException(pjp, e);
        }

        return resultBean;
    }

    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {

        logger.error("=================== Exception start ===================");
        logger.error(pjp.getSignature() + ", Exception message: ", e);
        logger.error("=================== Exception end ===================");

        return ResultBean.error(CodeMsg.SERVER_ERROR);
    }
}
