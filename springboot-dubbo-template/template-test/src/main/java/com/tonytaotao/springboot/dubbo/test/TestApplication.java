package com.tonytaotao.springboot.dubbo.test;

import com.tonytaotao.springboot.dubbo.common.config.MybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tonytaotao
 *
 * Configuration 类似于spring 配置文件，负责注册bean
 *
 * ComponentScan 注解类查找规则定义 <context:component-scan/>
 *
 * EnableAspectJAutoProxy 激活Aspect 自动代理 <aop:aspectj-autoproxy/>
 *      proxy-target-class : 默认为false 表示使用JDK 动态代理。如果实现了至少一个接口，Spring 会优先选择使用JDK 动态代理，若果没有实现任何接口，则spring 会选择CGLIB 动态代理，强制使用CGLIB 动态代理，使用以下配置
 *      exposeProxy : springAOP 只会拦截public 方法，不会拦截provided 和private 方法，并且不会拦截public 方法内部调用的其他方法，也就是说只会拦截代理对象的方法，即增强的是代理对象，而不是原对象, 设置就可以暴露出代理对象，拦截器会获取代理对象，并且将代理对象转换成原对象。从而对内部调用的方法进行增强
 *
 * EnableTransactionManagement 启用注解式事务管理 <tx:annotation-driven />  需要在yml中配置spring.datasource.*, 才会自动激活事务配置，然后在方法或类上加上@Transactional
 */
@SpringBootApplication
@RestController
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan({"com.tonytaotao.springboot.dubbo.test.*.mapper"})
@Import({MybatisPlusConfig.class})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
