package com.tonytaotao.springboot.dubbo.order.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.tonytaotao.springboot.dubbo.common.base.GlobalResult;
import com.tonytaotao.springboot.dubbo.order.OrderApplication;
import com.tonytaotao.springboot.dubbo.order.order.entity.UserOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
// classes 指定启动配置类，默认@SpringBootApplication 所修饰的类，引入spring 上下文
@SpringBootTest(classes = {OrderApplication.class})

// --------------启动真实web容器 ---------------------
// webEnvironment 启动web容器，例如tomcat
//@SpringBootTest(classes = {OrderApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)


// --------------启动mock web容器 --------------------
// 启动自动配置MockMVC,模拟web容器
//@AutoConfigureMockMvc


// --------------启动mvc web容器 --------------------
// 只启动 web 层的上下文 ,可以指定controller  @WebMvcTest(OrderController.class)
// 使用 @WebMvcTest方式时，只能初始化web层相关的bean, 如果有其他bean依赖，则需要一个个@MockBean出来，比较麻烦
//@WebMvcTest
class MockMvcTest {

    /*---------------------普通单元测试--------------------*/

    @Autowired
    private OrderController orderController;
    @Test
    void getOrderDetailById() {
        GlobalResult<UserOrder> globalResult = orderController.getOrderDetailById(1L);
        System.out.println(JSONObject.toJSONString(globalResult));
    }

    /*---------------------真实http测试--------------------*/
    // 配置了 webEnvironment ，真实的http请求
    /*
    @LocalServerPort
    private Integer port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Test
    void getOrderDetailById() {
        GlobalResult<UserOrder> globalResult = testRestTemplate.getForObject("http://localhost:" + port + "/order/order/order/getOrderDetail/1", GlobalResult.class);
        System.out.println(JSONObject.toJSONString(globalResult));
    }
    */

    /*---------------------mock http测试--------------------*/
    /*
    @Autowired
    private MockMvc mockMvc;

    // 配置这个的目的，是Mockmvc  会报 druid WebStatFilter 空指针异常
    // 第二种方式 web-stat-filter.enabled=false
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getOrderDetailById() throws Exception{

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/order/order/getOrderDetail/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String contentBody = mvcResult.getResponse().getContentAsString();
        System.out.println(contentBody);

    }*/
}