package com.hello.proxy.config.v1_dynamicproxy;

import com.hello.proxy.trace.LogTrace;
import com.hello.proxy.trace.TraceStatus;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.springframework.util.PatternMatchUtils;

public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isIgnoreMethod(method, args)) {
            return method.invoke(target, args);
        }

        TraceStatus status = null;
        try {
            status = logTrace.begin(target.getClass().getSimpleName() + "." + method.getName() + "()");

            Object result = method.invoke(target, args);

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

    private boolean isIgnoreMethod(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        if (!PatternMatchUtils.simpleMatch(patterns, method.getName())) {
            return true;
        }
        return false;
    }
}
