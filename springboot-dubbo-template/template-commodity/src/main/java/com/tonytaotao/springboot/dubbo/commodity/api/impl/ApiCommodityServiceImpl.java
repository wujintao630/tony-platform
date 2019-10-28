package com.tonytaotao.springboot.dubbo.commodity.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.tonytaotao.springboot.dubbo.api.ApiCommodityService;
import com.tonytaotao.springboot.dubbo.commodity.commodity.entity.Commodity;
import com.tonytaotao.springboot.dubbo.commodity.commodity.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = ApiCommodityService.class, version ="1.0.0",  timeout = 10000)
public class ApiCommodityServiceImpl implements ApiCommodityService {

    @Autowired
    private CommodityService commodityService;

    @Override
    @LcnTransaction
    @Transactional
    public boolean subCommodityStock(Long commodityId, Integer amount) {

        Commodity commodity = commodityService.getById(commodityId);
        if (commodity == null) {
            return false;
        }

        commodity.setStockAmount(commodity.getStockAmount() - amount);

        return commodityService.updateById(commodity);
    }
}
