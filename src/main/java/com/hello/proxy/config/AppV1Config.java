package com.hello.proxy.config;

import com.hello.proxy.app.v1.controller.OrderControllerV1;
import com.hello.proxy.app.v1.controller.OrderControllerV1Impl;
import com.hello.proxy.app.v1.repository.OrderRepositoryV1;
import com.hello.proxy.app.v1.repository.OrderRepositoryV1Impl;
import com.hello.proxy.app.v1.service.OrderServiceV1;
import com.hello.proxy.app.v1.service.OrderServiceV1Impl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppV1Config {

    @Bean
    public OrderControllerV1 orderControllerV1() {
        return new OrderControllerV1Impl(orderServiceV1());
    }
    @Bean
    public OrderServiceV1 orderServiceV1() {
        return new OrderServiceV1Impl(orderRepositoryV1());
    }
    @Bean
    public OrderRepositoryV1 orderRepositoryV1() {
        return new OrderRepositoryV1Impl();
    }
}
