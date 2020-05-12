package com.tonytaotao.service.producer.user.controller;

import com.tonytaotao.service.producer.user.entity.UserInfo;
import com.tonytaotao.service.producer.user.service.UserInfoService;
import com.tonytaotao.service.common.base.GlobalResult;
import com.tonytaotao.service.common.base.QueryPage;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

/**
* <p>
*      前端控制器
* </p>
 *
 * @author tonytaotao
 * @since 2018-08-11
*/
@RestController
@RequestMapping("/user/userInfo")
@Slf4j
public class UserInfoController {

    @Autowired
    UserInfoService service;

    /**
    *获取详细信息
     * @param id
     * @return
    */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "Path")
    @GetMapping("/getUserInfoDetail/{id}")
    public GlobalResult<UserInfo> getUserInfoDetailById(@PathVariable Long id) {
        return new GlobalResult<>(service.selectById(id));
    }

    /**
     *分页获取列表
     * @param query
     * @return
     */
    @ApiOperation(value = "分页获取详细信息，带上查询条件", notes = "根据查询条件分页获取明细")
    @PostMapping("/getUserInfoPage")
    public GlobalResult<Page<List<UserInfo>>> getUserInfoPage(@RequestBody @ApiParam(value = "查询条件json对象", required = true) QueryPage<UserInfo> query) {
        Page page = service.selectPage(query.getPage(), new EntityWrapper<>(query.getQueryEntity()));
        return new GlobalResult<>(page);
    }

    /**
    * 新增或者更新信息
     * @param entity
     *@return
    */
    @ApiOperation(value = "新增或者更新信息", notes = "新增或者更新信息")
    @ApiImplicitParam(name = "entity", value = "要保存的json对象", required = true, paramType = "body", dataType = "UserInfo")
    @PostMapping("/saveOrUpdateUserInfo")
    public GlobalResult saveOrUpdateUserInfo(@RequestBody UserInfo entity) {
        service.saveOrUpdateUserInfo(entity);
        return GlobalResult.DefaultSuccess();
    }

    /**
     *删除信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除信息", notes = "根据url的id来删除信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "Path")
    @DeleteMapping("/deleteUserInfoById/{id}")
    public GlobalResult<Boolean> deleteUserInfoById(@PathVariable Long id) {
        return new GlobalResult<>(service.deleteUserInfoById(id));
    }
}
