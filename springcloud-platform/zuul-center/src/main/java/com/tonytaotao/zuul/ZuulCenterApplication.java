package com.tonytaotao.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableZuulProxy
@RestController
public class ZuulCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulCenterApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
