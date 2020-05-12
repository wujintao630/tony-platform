package com.tonytaotao.springboot.dubbo.commodity.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tonytaotao.springboot.dubbo.commodity.commodity.entity.Commodity;

/**
 * <p>
 *  服务接口类
 * </p>
 *
 * @author tonytaotao
 * @since 2019-10-25
 */
public interface CommodityService extends IService<Commodity> {

    void saveOrUpdateCommodity(Commodity entity);

    Boolean deleteCommodityById(Long id);
}
