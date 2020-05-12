package com.tonytaotao.springboot.dubbo.common.config;

import com.alibaba.dubbo.config.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * dubbo配置
 * @author tonytaotao
 */
@Configuration
public class DubboConsumerConfig {

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(1000);
        return consumerConfig;
    }

}
