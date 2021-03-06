package com.kayafirat.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class EventLogging {
    @Pointcut("within(com.kayafirat.service.Impl..*)")
    public void eventPointCut() { }

    @Pointcut("execution(* com.kayafirat.service.ImageService.saveImage(..))")
    public void excludePointCutImage() {}

    @Pointcut("execution(* com.kayafirat.service.UserService.updateUserImage(..))")
    public void excludePointCutUpdateImage() {}


    @Around("eventPointCut() && (!excludePointCutImage() && !excludePointCutUpdateImage())")
    public Object eventLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        log.debug(className+" classındaki "+methodName+"() methoda  istek yapıldı. \tArgs:"+mapper.writeValueAsString(joinPoint.getArgs()));
        Object object = joinPoint.proceed();
        log.debug(className+" classındaki "+methodName+"() methodun cevabı döndürüldü \tArgs:"+mapper.writeValueAsString(object));
        return object;
    }

}
