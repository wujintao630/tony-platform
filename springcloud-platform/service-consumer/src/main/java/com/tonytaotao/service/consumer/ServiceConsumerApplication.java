package com.tonytaotao.service.consumer;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @SpringBootApplication  已经包含了  @EnableCircuitBreaker
 */
@SpringBootApplication
@RestController
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
public class ServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerApplication.class, args);
    }

    /**
     *springboot 2.0以上配置熔断器监控时，需要配置此bean，否则监控页面进不去
     *
     * 访问方式, ip和port均为当前应用
     *  http://ip:port/hystrix
     *  然后在页面中间输入框中 输入 http://ip:port/actuator/hystrix.stream
     *  点击 monitor stream即可进入监控页面
     *
     */
    @Bean
    public ServletRegistrationBean getServlet(){

        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();

        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);

        registrationBean.setLoadOnStartup(1);

        registrationBean.addUrlMappings("/actuator/hystrix.stream");

        registrationBean.setName("HystrixMetricsStreamServlet");


        return registrationBean;
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
