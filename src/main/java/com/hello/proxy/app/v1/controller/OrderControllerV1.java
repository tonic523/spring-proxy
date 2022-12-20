package com.hello.proxy.app.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 스프링은 @Controller 또는 @RequestMapping 이 있어야 스프링 컨트롤러로 인식
@RequestMapping
@ResponseBody
public interface OrderControllerV1 {

    // 로그 적용 대상
    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    // 로그를 적용하지 않을 대상
    @GetMapping("/v1/no-log")
    String noLog();
}

// @RequestMapping: 스프링MVC는 타입에 @Controller, @RequestMapping 애노테이션이 있어야 컨트롤러로 인식
