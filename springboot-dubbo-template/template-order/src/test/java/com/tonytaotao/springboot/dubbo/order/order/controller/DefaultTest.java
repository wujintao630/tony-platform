package com.tonytaotao.springboot.dubbo.order.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.tonytaotao.springboot.dubbo.order.OrderApplication;
import com.tonytaotao.springboot.dubbo.order.order.entity.UserOrder;
import com.tonytaotao.springboot.dubbo.order.order.mapper.OrderMapper;
import com.tonytaotao.springboot.dubbo.order.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OrderApplication.class})
class DefaultTest {

    @Autowired
    private OrderController orderController;

    /*@MockBean
    private OrderService orderServiceMock;*/

    @SpyBean
    private OrderService orderServiceSpy;

    @Test
    void getOrderDetailById() {

        UserOrder userOrder = null;

        /*// 测试 no mock : 直接调用mock类中的方法，不会调用真实方法，直接返回 null
        UserOrder userOrder = orderServiceMock.getById(1L);
        System.out.println(" no mock : " + JSONObject.toJSONString(userOrder));

        // 测试 mock : 会返回 thenReturn 自定义数据
        when(orderServiceMock.getById(1L)).thenReturn(new UserOrder().setId(2L));
        userOrder = orderServiceMock.getById(1L);
        System.out.println(" mock : " + JSONObject.toJSONString(userOrder));*/

        // 测试 no spy : 直接调用spy类中的方法，会调用真实方法，返回真实结果
        userOrder = orderServiceSpy.getById(1L);
        System.out.println(" no spy : " + JSONObject.toJSONString(userOrder));

        // 测试 spy : 会返回 doReturn 自定义数据
        doReturn(new UserOrder().setId(3L)).when(orderServiceSpy).getById(1L);
        //when(orderServiceSpy.getById(1L)).thenReturn(new UserOrder().setId(3L));
        userOrder = orderServiceSpy.getById(1L);
        System.out.println(" spy : " + JSONObject.toJSONString(userOrder));

    }


}