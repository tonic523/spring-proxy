package com.hello.proxy.config;

import com.hello.proxy.trace.LogTrace;
import com.hello.proxy.app.v2.controller.OrderControllerConcreteProxy;
import com.hello.proxy.app.v2.controller.OrderControllerV2;
import com.hello.proxy.app.v2.repository.OrderRepositoryConcreteProxy;
import com.hello.proxy.app.v2.repository.OrderRepositoryV2;
import com.hello.proxy.app.v2.service.OrderServiceConcreteProxy;
import com.hello.proxy.app.v2.service.OrderServiceV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        OrderControllerV2 controllerImpl = new OrderControllerV2(orderServiceV2(logTrace));
        return new OrderControllerConcreteProxy(controllerImpl, logTrace);
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        OrderServiceV2 serviceImpl = new OrderServiceV2(orderRepositoryV2(logTrace));
        return new OrderServiceConcreteProxy(serviceImpl, logTrace);
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        OrderRepositoryV2 orderRepositoryV2 = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(orderRepositoryV2, logTrace);
    }
}
