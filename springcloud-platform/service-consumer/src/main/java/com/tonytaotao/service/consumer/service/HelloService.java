package com.tonytaotao.service.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *  根据服务名称来指定服务提供方
 * 设置fallback 后，当调用远程服务失败后，默认会转到 HelloServiceImpl.class对应的方法中
 */
@FeignClient(name = "service-producer", fallback = HelloServiceImpl.class)
public interface HelloService {

    /**
     *通过注解来指定访问方式，访问路径，访问参数
     */
    @GetMapping("/user/userInfo/getUserInfoDetail/{id}")
    Object hello(@PathVariable(value = "id") Long id);
}
