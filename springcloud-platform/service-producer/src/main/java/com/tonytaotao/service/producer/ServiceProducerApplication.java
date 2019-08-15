package com.tonytaotao.service.producer;

import com.tonytaotao.service.common.config.MybatisPlusConfig;
import com.tonytaotao.service.common.config.TransationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Import({MybatisPlusConfig.class,TransationConfig.class})
 * 这个的作用是引入公共部分的mapper和transaction配置，因为公共部分jar包的包路径为com.tonytaotao.service.common.*
 * 但是  @SpringBootApplication  这个注解中默认扫描配置是从当前所在类的包开始的，而当前的包路径为 com.tonytaotao.service.producer
 * 所以扫不到那两个配置，加上这个就是强制引入这两个配置
 */
@SpringBootApplication
@RestController
@EnableSwagger2
@Import({MybatisPlusConfig.class,TransationConfig.class})
public class ServiceProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProducerApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
