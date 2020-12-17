package com.tonytaotao.springboot.dubbo.order.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tonytaotao.springboot.dubbo.common.base.GlobalResult;
import com.tonytaotao.springboot.dubbo.common.base.QueryPage;
import com.tonytaotao.springboot.dubbo.order.order.entity.UserOrder;
import com.tonytaotao.springboot.dubbo.order.order.service.OrderService;
import com.tonytaotao.springboot.dubbo.order.order.vo.PlaceOrderReq;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* <p>
*      前端控制器
* </p>
 *
 * @author tonytaotao
 * @since 2019-10-25
*/
@RestController
@RequestMapping("/order/order")
@Slf4j
public class OrderController {

    @Autowired
    OrderService service;

    /**
     *下订单
     * @param placeOrderReq
     * @return
     */
    @ApiOperation(value = "下订单", notes = "下订单")
    @PostMapping("/placeOrder")
    public GlobalResult<Boolean> placeOrder(@RequestBody @ApiParam(value = "下单请求json对象", required = true) @Validated PlaceOrderReq placeOrderReq) {
        return new GlobalResult<>(service.placeOrder(placeOrderReq));
    }

    /**
    *获取详细信息
     * @param id
     * @return
    */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "Path")
    @GetMapping("/getOrderDetail/{id}")
    public GlobalResult<UserOrder> getOrderDetailById(@PathVariable Long id) {
        return new GlobalResult<>(service.getById(id));
    }

    /**
     *分页获取列表
     * @param query
     * @return
     */
    @ApiOperation(value = "分页获取详细信息，带上查询条件", notes = "根据查询条件分页获取明细")
    @PostMapping("/getOrderPage")
    public GlobalResult<IPage<List<UserOrder>>> getOrderPage(@RequestBody @ApiParam(value = "查询条件json对象", required = true) QueryPage<UserOrder> query) {
        IPage page = service.page(query.getPage(), new QueryWrapper<>(query.getQueryEntity()));
        return new GlobalResult<>(page);
    }

    /**
    * 新增或者更新信息
     * @param entity
     *@return
    */
    @ApiOperation(value = "新增或者更新信息", notes = "新增或者更新信息")
    @ApiImplicitParam(name = "entity", value = "要保存的json对象", required = true, paramType = "body", dataType = "UserOrder")
    @PostMapping("/saveOrUpdateOrder")
    public GlobalResult saveOrUpdateOrder(@RequestBody UserOrder entity) {
        service.saveOrUpdate(entity);
        return GlobalResult.DefaultSuccess();
    }

    /**
     *删除信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除信息", notes = "根据url的id来删除信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "Path")
    @DeleteMapping("/deleteOrderById/{id}")
    public GlobalResult<Boolean> deleteOrderById(@PathVariable Long id) {
        return new GlobalResult<>(service.removeById(id));
    }
}
