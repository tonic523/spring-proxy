package com.hello.proxy.config.v6_aop;

import com.hello.proxy.trace.LogTrace;
import com.hello.proxy.config.AppV1Config;
import com.hello.proxy.config.AppV2Config;
import com.hello.proxy.config.v6_aop.aspect.LogTraceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AopConfig {

    // @Aspect가 있어도 스프링 빈으로 등록해주어야 한다.
    // 또는 LogTraceAspect에 @Component 애노테이션을 봍여서 컴포넌트 스캔을 사용해서 스프링 빈으로 등록할 수 있다.
    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
}
