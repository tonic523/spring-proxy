package com.hello.proxy.config;

import com.hello.proxy.app.trace.LogTrace;
import com.hello.proxy.app.v1.controller.OrderControllerProxy;
import com.hello.proxy.app.v1.controller.OrderControllerV1;
import com.hello.proxy.app.v1.controller.OrderControllerV1Impl;
import com.hello.proxy.app.v1.repository.OrderRepositoryProxy;
import com.hello.proxy.app.v1.repository.OrderRepositoryV1;
import com.hello.proxy.app.v1.repository.OrderRepositoryV1Impl;
import com.hello.proxy.app.v1.service.OrderServiceProxy;
import com.hello.proxy.app.v1.service.OrderServiceV1;
import com.hello.proxy.app.v1.service.OrderServiceV1Impl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1Impl controllerImpl = new
                OrderControllerV1Impl(orderService(logTrace));
        return new OrderControllerProxy(controllerImpl, logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl serviceImpl = new
                OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceProxy(serviceImpl, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl repositoryV1 = new OrderRepositoryV1Impl();
        return new OrderRepositoryProxy(repositoryV1, logTrace);
    }
}
