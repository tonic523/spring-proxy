package com.hello.proxy;

import com.hello.proxy.app.trace.LogTrace;
import com.hello.proxy.app.trace.ThreadLocalLogTrace;
import com.hello.proxy.config.v5_autoproxy.AutoProxyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AutoProxyConfig.class)
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
