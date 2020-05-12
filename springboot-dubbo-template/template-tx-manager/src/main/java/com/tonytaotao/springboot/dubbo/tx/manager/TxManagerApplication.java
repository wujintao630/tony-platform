package com.tonytaotao.springboot.dubbo.tx.manager;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tonytaotao
 */
@SpringBootApplication
@RestController
@EnableTransactionManagerServer
public class TxManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxManagerApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
