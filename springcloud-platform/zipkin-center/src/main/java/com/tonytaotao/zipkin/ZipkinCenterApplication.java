package com.tonytaotao.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZipkinCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinCenterApplication.class, args);
    }
}
