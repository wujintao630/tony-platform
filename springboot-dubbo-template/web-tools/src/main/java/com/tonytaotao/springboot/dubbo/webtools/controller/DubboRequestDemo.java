package com.tonytaotao.springboot.dubbo.webtools.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @desc: dubbo请求实体
 * @author: hbt
 * @date: 2020/08/27
 **/
@Data
public class DubboRequestDemo implements Serializable {

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 请求Ip
     */
    private String requestIp;

    /**
     * dubbo zk注册中心
     */
    private String address;

    /**
     * 接口版本
     */
    private String version;

    /**
     * dubbo service名称
     */
    @NotEmpty(message = "serviceClass不能为空")
    private String serviceClass;

    /**
     * service 方法
     */
    @NotEmpty(message = "method不能为空")
    private String method;

    /**
     * 请求参数
     */
    private List<Object> body;
}
