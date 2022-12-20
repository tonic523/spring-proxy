package com.hello.proxy;

import com.hello.proxy.config.AppV1Config;
import com.hello.proxy.config.AppV2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/*
 * @Import(AppV1Config.class): 클래스를 스프링 빈으로 등록한다.
 * @SpringBootApplication(scanBasePackages = "com.hello.proxy.v1"): 컨포넌트스캔을 시작할 위치를 지정, 해당 패키지와 하위 패키지를
 * 컴포넌트 스캔한다.
 */
@Import({AppV1Config.class, AppV2Config.class})
@SpringBootApplication(scanBasePackages = "com.hello.proxy.app")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}
