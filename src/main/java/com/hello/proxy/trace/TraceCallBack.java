package com.hello.proxy.trace;

@FunctionalInterface
public interface TraceCallBack<T> {

    T call();
}
