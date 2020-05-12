package com.tonytaotao.springboot.dubbo.commodity.commodity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tonytaotao.springboot.dubbo.commodity.commodity.entity.Commodity;
import com.tonytaotao.springboot.dubbo.commodity.commodity.service.CommodityService;
import com.tonytaotao.springboot.dubbo.common.base.GlobalResult;
import com.tonytaotao.springboot.dubbo.common.base.QueryPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/commodity/commodity")
@Slf4j
public class CommodityController {

    @Autowired
    CommodityService service;

    /**
    *获取详细信息
     * @param id
     * @return
    */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "Path")
    @GetMapping("/getCommodityDetail/{id}")
    public GlobalResult<Commodity> getCommodityDetailById(@PathVariable Long id) {
        return new GlobalResult<>(service.getById(id));
    }

    /**
     *分页获取列表
     * @param query
     * @return
     */
    @ApiOperation(value = "分页获取详细信息，带上查询条件", notes = "根据查询条件分页获取明细")
    @PostMapping("/getCommodityPage")
    public GlobalResult<IPage<List<Commodity>>> getCommodityPage(@RequestBody @ApiParam(value = "查询条件json对象", required = true) QueryPage<Commodity> query) {
        IPage page = service.page(query.getPage(), new QueryWrapper<>(query.getQueryEntity()));
        return new GlobalResult<>(page);
    }

    /**
    * 新增或者更新信息
     * @param entity
     *@return
    */
    @ApiOperation(value = "新增或者更新信息", notes = "新增或者更新信息")
    @ApiImplicitParam(name = "entity", value = "要保存的json对象", required = true, paramType = "body", dataType = "Commodity")
    @PostMapping("/saveOrUpdateCommodity")
    public GlobalResult saveOrUpdateCommodity(@RequestBody Commodity entity) {
        service.saveOrUpdateCommodity(entity);
        return GlobalResult.DefaultSuccess();
    }

    /**
     *删除信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除信息", notes = "根据url的id来删除信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "Path")
    @DeleteMapping("/deleteCommodityById/{id}")
    public GlobalResult<Boolean> deleteCommodityById(@PathVariable Long id) {
        return new GlobalResult<>(service.deleteCommodityById(id));
    }
}
