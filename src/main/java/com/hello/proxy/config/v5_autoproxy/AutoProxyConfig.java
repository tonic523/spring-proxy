package com.hello.proxy.config.v5_autoproxy;

import com.hello.proxy.trace.LogTrace;
import com.hello.proxy.config.AppV1Config;
import com.hello.proxy.config.AppV2Config;
import com.hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

//    @Bean
//    public Advisor advisor1(LogTrace logTrace) {
//        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
//        pointcut.setMappedNames("request*", "order*", "save*");
//        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
//        return new DefaultPointcutAdvisor(pointcut, advice);
//    }

//    @Bean
//    public Advisor advisor2(LogTrace logTrace) {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        // * : 모든 반환 타입
//        // hello.proxy.app..: 해당 패키지와 그 하위 패키지
//        // *(..): * 모든 메서드 이름, (..) 파라미터는 상관 없음
//        pointcut.setExpression("execution(* com.hello.proxy.app..*(..))");
//        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
//        return new DefaultPointcutAdvisor(pointcut, advice);
//    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.hello.proxy.app..*(..)) && "
                + "!execution(* com.hello.proxy.app..noLog(..))");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
