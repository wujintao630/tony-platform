package com.tonytaotao.springboot.dubbo.api;

/**
 * RPC 商品服务
 * @author tonytaotao
 */
public interface ApiCommodityService {

    /**
     * 扣减商品库存
     * @param commodityId
     * @param amount
     * @return
     */
    boolean subCommodityStock(Long commodityId, Integer amount);

}
