package ecom.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {

    @Before("execution(* ecom.repository.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[LOG] Calling: " + joinPoint.getSignature().getName());
    }

    @After("execution(* ecom.repository.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[LOG] Completed: " + joinPoint.getSignature().getName());
    }
}
