package com.tonytaotao.springboot.dubbo.commodity.commodity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tonytaotao.springboot.dubbo.commodity.commodity.entity.Commodity;
import com.tonytaotao.springboot.dubbo.commodity.commodity.mapper.CommodityMapper;
import com.tonytaotao.springboot.dubbo.commodity.commodity.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务接口实现类
 * </p>
 *
 * @author tonytaotao
 * @since 2019-10-25
 */
@Service
@Slf4j
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Override
    public void saveOrUpdateCommodity(Commodity entity) {
        this.saveOrUpdate(entity);
    }

    @Override
    public Boolean deleteCommodityById(Long id) {
        return this.removeById(id);
    }

}
