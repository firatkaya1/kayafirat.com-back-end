package com.firatkaya.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
@Slf4j
public class After {

    @Before("execution(* com.firatkaya.service.Impl.*.*(..))")
    public void before(JoinPoint joinPoint) {
        log.debug("Hello from Logback ", joinPoint.getSignature().getDeclaringTypeName());
        log.debug("This is an INFO :"+ Arrays.stream(joinPoint.getArgs()).toArray().toString());
    }
}
