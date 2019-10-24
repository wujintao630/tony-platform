package com.tonytaotao.springboot.dubbo.api.impl;

import com.tonytaotao.springboot.dubbo.user.entity.UserInfo;
import com.tonytaotao.springboot.dubbo.user.service.UserInfoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * //@Service必须使用dubbo提供的
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    UserInfoService userInfoService;

    @Override
    public String getName(Long id) {
        UserInfo userInfo =  userInfoService.getById(id);

        return userInfo == null? "未知：无数据" : userInfo.getName();
    }
}
