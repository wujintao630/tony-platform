package com.tonytaotao.springboot.dubbo.order.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tonytaotao.springboot.dubbo.order.order.entity.UserOrder;
import com.tonytaotao.springboot.dubbo.order.order.vo.PlaceOrderReq;

/**
 * <p>
 *  服务接口类
 * </p>
 *
 * @author tonytaotao
 * @since 2019-10-25
 */
public interface OrderService extends IService<UserOrder> {

    Boolean  placeOrder(PlaceOrderReq placeOrderReq);

}
