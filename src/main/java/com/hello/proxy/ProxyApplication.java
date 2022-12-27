package com.hello.proxy;

import com.hello.proxy.trace.LogTrace;
import com.hello.proxy.trace.ThreadLocalLogTrace;
import com.hello.proxy.config.v6_aop.AopConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AopConfig.class)
@SpringBootApplication(scanBasePackages = "com.hello.proxy.app")
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

}
