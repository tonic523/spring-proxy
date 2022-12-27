package com.hello.proxy.config.v4_postprocessor;

import com.hello.proxy.trace.LogTrace;
import com.hello.proxy.config.AppV1Config;
import com.hello.proxy.config.AppV2Config;
import com.hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import com.hello.proxy.config.v4_postprocessor.postprocessor.PackageLogTraceProxyPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class BeanPostProcessorConfig {

    @Bean
    public PackageLogTraceProxyPostProcessor logTraceProxyPostProcessor(LogTrace logTrace) {
        return new PackageLogTraceProxyPostProcessor("com.hello.proxy.app", getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        // advisor = pointcut + advice
        return new DefaultPointcutAdvisor(pointcut, advice);

    }
}
