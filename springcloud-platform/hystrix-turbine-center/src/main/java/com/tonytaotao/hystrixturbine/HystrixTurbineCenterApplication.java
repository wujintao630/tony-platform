package com.tonytaotao.hystrixturbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableTurbine
@EnableHystrixDashboard
public class HystrixTurbineCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixTurbineCenterApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
