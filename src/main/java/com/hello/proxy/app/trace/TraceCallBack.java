package com.hello.proxy.app.trace;

@FunctionalInterface
public interface TraceCallBack<T> {

    T call();
}
