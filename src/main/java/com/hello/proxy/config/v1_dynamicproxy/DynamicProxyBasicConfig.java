package com.hello.proxy.config.v1_dynamicproxy;

import com.hello.proxy.trace.LogTrace;
import com.hello.proxy.app.v1.controller.OrderControllerV1;
import com.hello.proxy.app.v1.controller.OrderControllerV1Impl;
import com.hello.proxy.app.v1.repository.OrderRepositoryV1;
import com.hello.proxy.app.v1.repository.OrderRepositoryV1Impl;
import com.hello.proxy.app.v1.service.OrderServiceV1;
import com.hello.proxy.app.v1.service.OrderServiceV1Impl;
import com.hello.proxy.config.v1_dynamicproxy.handler.LogTraceBasicHandler;
import java.lang.reflect.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicProxyBasicConfig {

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 target = new OrderControllerV1Impl(orderServiceV1(logTrace));
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
                new Class[] {OrderControllerV1.class}, handler);
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1 target = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(),
                new Class[] {OrderServiceV1.class}, handler);
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1Impl target = new OrderRepositoryV1Impl();
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(),
                new Class[] {OrderRepositoryV1.class}, handler);
    }
}
