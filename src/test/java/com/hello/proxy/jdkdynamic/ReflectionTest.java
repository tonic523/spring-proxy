package com.hello.proxy.jdkdynamic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello(); //공통 로직1 시작
        log.info("start");
        String result1 = target.callA(); //호출하는 메서드가 다름
        log.info("result={}", result1); //공통 로직1 종료
        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB(); //호출하는 메서드가 다름
        log.info("result={}", result2);
        //공통 로직2 종료
    }

    @Test
    void reflection1() throws Exception {
        Class<Hello> classHello = Hello.class;

        Hello target = new Hello();

        //callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);
        //callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result2);
    }

    /*
     *  메타 정보로 callA(), callB() 코드를 Method라는 메타정보로 추상화
     */
    @Test
    void reflection2() throws Exception {
        Class<Hello> classHello = Hello.class;

        Hello target = new Hello();

        //callA, callB 메서드 정보
        List<Method> methods = List.of(
                classHello.getMethod("callA"), classHello.getMethod("callB")
        );

        methods.forEach(
                method -> {
                    try {
                        dynamicCall(target, method);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void dynamicCall(Hello target, Method method) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result = {}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
