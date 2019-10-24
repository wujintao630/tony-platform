package com.tonytaotao.springboot.dubbo.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tonytaotao.springboot.dubbo.account.entity.UserAccount;
import com.tonytaotao.springboot.dubbo.account.service.UserAccountService;
import com.tonytaotao.springboot.dubbo.api.impl.HelloService;
import com.tonytaotao.springboot.dubbo.common.base.GlobalResult;
import com.tonytaotao.springboot.dubbo.common.base.QueryPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* <p>
*      前端控制器
* </p>
 *
 * @author wujintao
 * @since 2019-10-22
*/
@RestController
@RequestMapping("/account/userAccount")
@Slf4j
public class UserAccountController {

    @Autowired
    UserAccountService service;

    /**
     * 不能使用@Autowired自动注入，而要用Dubbo提供的@Reference注解
     */
    @Reference
    private HelloService helloService;

    /**
     *获取名称
     * @param id
     * @return
     */
    @ApiOperation(value = "获取名称", notes = "根据url的id来获取获取名称")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "Path")
    @GetMapping("/getUserNameById/{id}")
    public GlobalResult<String> getUserNameById(@PathVariable Long id) {
        return new GlobalResult<>(helloService.getName(id));
    }

    /**
    *获取详细信息
     * @param id
     * @return
    */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "Path")
    @GetMapping("/getUserAccountDetail/{id}")
    public GlobalResult<UserAccount> getUserAccountDetailById(@PathVariable Long id) {
        return new GlobalResult<>(service.getById(id));
    }

    /**
     *分页获取列表
     * @param query
     * @return
     */
    @ApiOperation(value = "分页获取详细信息，带上查询条件", notes = "根据查询条件分页获取明细")
    @PostMapping("/getUserAccountPage")
    public GlobalResult<IPage<List<UserAccount>>> getUserAccountPage(@RequestBody @ApiParam(value = "查询条件json对象", required = true) QueryPage<UserAccount> query) {
        IPage page = service.page(query.getPage(), new QueryWrapper<>(query.getQueryEntity()));
        return new GlobalResult<>(page);
    }

    /**
    * 新增或者更新信息
     * @param entity
     *@return
    */
    @ApiOperation(value = "新增或者更新信息", notes = "新增或者更新信息")
    @ApiImplicitParam(name = "entity", value = "要保存的json对象", required = true, paramType = "body", dataType = "UserAccount")
    @PostMapping("/saveOrUpdateUserAccount")
    public GlobalResult saveOrUpdateUserAccount(@RequestBody UserAccount entity) {
        service.saveOrUpdateUserAccount(entity);
        return GlobalResult.DefaultSuccess();
    }

    /**
     *删除信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除信息", notes = "根据url的id来删除信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "Path")
    @DeleteMapping("/deleteUserAccountById/{id}")
    public GlobalResult<Boolean> deleteUserAccountById(@PathVariable Long id) {
        return new GlobalResult<>(service.deleteUserAccountById(id));
    }
}
