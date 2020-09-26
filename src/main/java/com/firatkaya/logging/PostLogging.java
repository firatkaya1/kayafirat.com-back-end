package com.firatkaya.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class PostLogging {

    @Before("execution(* com.firatkaya.service.Impl.PostServiceImp.getPost())")
    public void beforeDeletePost(JoinPoint joinPoint) {
        System.out.println("Before methodu calisti:: "+joinPoint.getArgs()[0]);

    }
    @org.aspectj.lang.annotation.After("execution(* com.firatkaya.service.PostService.getPost())")
    public void afterDeletePost(JoinPoint joinPoint) {
        System.out.println("after çalıştı");
    }
}
