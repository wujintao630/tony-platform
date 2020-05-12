package com.tonytaotao.springboot.dubbo.common.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * dubbo配置
 * @author tonytaotao
 */
@Configuration
@NacosPropertySource(dataId = "init-config", groupId = "template-account")
public class DubboProviderConfig {

    @NacosValue(value = "${dubbo.applicationName}")
    private String dubboApplicationName;
    @NacosValue(value = "${dubbo.protocolName}")
    private String dubboProtocolName;
    @NacosValue(value = "${dubbo.protlcolPort}")
    private Integer dubboProtocolPort;
    @NacosValue(value = "${dubbo.registryAddress}")
    private String dubboRegistryAddress;

    /*@Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboApplicationName);
        return applicationConfig;
    }*/

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(dubboProtocolName);
        protocolConfig.setPort(dubboProtocolPort);
        return protocolConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboRegistryAddress);
        return registryConfig;
    }

    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(1000);
        return providerConfig;
    }

}
