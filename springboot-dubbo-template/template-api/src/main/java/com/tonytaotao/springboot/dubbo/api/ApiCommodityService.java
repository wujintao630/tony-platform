package com.tonytaotao.springboot.dubbo.api;

public interface ApiCommodityService {

    boolean subCommodityStock(Long commodityId, Integer amount);

}
