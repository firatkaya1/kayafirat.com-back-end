package com.firatkaya.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class After {

    @Pointcut("execution(* com.firatkaya.service.Impl.*.*(..))")
    public void before(JoinPoint joinPoint) {
        System.out.println("bu method çalıştı." +joinPoint);
    }
}
