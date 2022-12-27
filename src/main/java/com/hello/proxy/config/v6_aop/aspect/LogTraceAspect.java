package com.hello.proxy.config.v6_aop.aspect;

import com.hello.proxy.trace.LogTrace;
import com.hello.proxy.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect // 애노테이션 기반 프록시를 적용할 때 필요
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* com.hello.proxy.app..*(..))") // 포인트컷 표현식을 넣는다.
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;

        log.info("target={}", joinPoint.getTarget());
        log.info("getArgs={}", joinPoint.getArgs());
        log.info("getSignature={}", joinPoint.getSignature());

        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // 로직 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
