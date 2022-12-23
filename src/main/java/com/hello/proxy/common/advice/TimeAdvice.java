package com.hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/*
 * MethodInterceptor는 Interceptor를 상속하고 Interceptor는 Advice 인터페이스를 상속한다.
 * invocation.proceed()를 호출하면 target 클래스를 호출하고 그 결과를 받는다.
 * 이 때 MethodInvocation invocation 안에 target 클래스의 정보를 모두 포함하고 있다.
 */

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");

        long startTime = System.currentTimeMillis();
        Object result = invocation.proceed();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("TimeProxy 종료 resultTime={}ms", resultTime);
        return result;
    }
}
