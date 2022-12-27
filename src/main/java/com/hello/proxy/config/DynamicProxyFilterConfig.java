package com.hello.proxy.config;

import com.hello.proxy.trace.LogTrace;
import com.hello.proxy.app.v1.controller.OrderControllerV1;
import com.hello.proxy.app.v1.controller.OrderControllerV1Impl;
import com.hello.proxy.app.v1.repository.OrderRepositoryV1;
import com.hello.proxy.app.v1.repository.OrderRepositoryV1Impl;
import com.hello.proxy.app.v1.service.OrderServiceV1;
import com.hello.proxy.app.v1.service.OrderServiceV1Impl;
import com.hello.proxy.config.v1_dynamicproxy.LogTraceFilterHandler;
import java.lang.reflect.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicProxyFilterConfig {

    public static final String[] PATTERNS = {"request*", "order*", "save*"};

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 orderController = new
                OrderControllerV1Impl(orderServiceV1(logTrace));
        OrderControllerV1 proxy = (OrderControllerV1)
                Proxy.newProxyInstance(DynamicProxyFilterConfig.class.getClassLoader(),
                        new Class[]{OrderControllerV1.class},
                        new LogTraceFilterHandler(orderController, logTrace, PATTERNS)
                );
        return proxy;
    }
    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1 orderService = new
                OrderServiceV1Impl(orderRepositoryV1(logTrace));
        OrderServiceV1 proxy = (OrderServiceV1)
                Proxy.newProxyInstance(DynamicProxyFilterConfig.class.getClassLoader(),
                        new Class[]{OrderServiceV1.class},
                        new LogTraceFilterHandler(orderService, logTrace, PATTERNS)
                );
        return proxy;
    }
    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();
        OrderRepositoryV1 proxy = (OrderRepositoryV1)
                Proxy.newProxyInstance(DynamicProxyFilterConfig.class.getClassLoader(),
                        new Class[]{OrderRepositoryV1.class},
                        new LogTraceFilterHandler(orderRepository, logTrace, PATTERNS)
                );
        return proxy;
    }
}
