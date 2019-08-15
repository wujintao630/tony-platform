package com.tonytaotao.service.consumer.service;

import org.springframework.stereotype.Component;

/**
 * 当远程服务不可用时，hystrix 如果配置了 fallback中的内容为此类，则直接返回此类中的对应接口的数据
 */
@Component
public class HelloServiceImpl  implements HelloService{
    @Override
    public Object hello(Long id) {
        return "remote error,this data get from local!";
    }
}
