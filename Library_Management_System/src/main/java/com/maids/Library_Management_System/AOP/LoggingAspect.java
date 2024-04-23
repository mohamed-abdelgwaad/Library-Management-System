package com.maids.Library_Management_System.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    @Pointcut("within(com.maids.Library_Management_System.Services..*) || within(com.maids.Library_Management_System.Security.Services..*)")
    public void applicationPackagePointcut() { }

    @Before("applicationPackagePointcut()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Entering in Method :  " + joinPoint.getSignature().getName());
    }

   
    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method Return : " + joinPoint.getSignature().getName() + " with result : " + result);
    }


    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
        logger.error("Cause : " + exception.getCause());
    }


    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            logger.info("Method execution time for " + joinPoint.getSignature().getName() + " : " + elapsedTime + " milliseconds.");
            return result;
        } catch (Exception e) {
            long elapsedTime = System.currentTimeMillis() - start;
            logger.error("Method execution time for " + joinPoint.getSignature().getName() + " with exception : " + elapsedTime + " milliseconds.");
            throw e;
        }
    }
}
