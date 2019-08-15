package com.tonytaotao.service.consumer.controller;

import com.tonytaotao.service.consumer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/{userId}")
    public Object hello(@PathVariable("userId") Long userId) {
        return helloService.hello(userId);
    }

}
