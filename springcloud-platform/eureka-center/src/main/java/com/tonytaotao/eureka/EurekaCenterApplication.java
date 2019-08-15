package com.tonytaotao.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * 在客户端中  @EnableEurekaClient 已经包含，无需显示注解也可以注册到eureka
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaCenterApplication.class, args);
    }
}
