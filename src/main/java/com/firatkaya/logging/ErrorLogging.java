package com.firatkaya.logging;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class ErrorLogging {

    @Pointcut("within(com.firatkaya.service.Impl..*)")
    public void exceptionPointCut() { }


    @AfterThrowing(value = "exceptionPointCut()", throwing="ex")
    public void exceptionServiceLogging(Exception ex) {
        log.error("Error :"+ex.getMessage());
    }
}