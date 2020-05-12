package com.tonytaotao.springboot.dubbo.account.account.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tonytaotao.springboot.dubbo.account.account.entity.UserAccount;
import com.tonytaotao.springboot.dubbo.account.account.mapper.UserAccountMapper;
import com.tonytaotao.springboot.dubbo.account.account.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务接口实现类
 * </p>
 *
 * @author tonytaotao
 * @since 2019-10-22
 */
@Service
@Slf4j
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

    @Override
    public void saveOrUpdateUserAccount(UserAccount entity) {
        this.saveOrUpdate(entity);
    }

    @Override
    public Boolean deleteUserAccountById(Long id) {
        return this.removeById(id);
    }

    @Override
    @SentinelResource(value = "saveOrder", blockHandler = "saveOrderExHandler", fallback = "saveOrderFallback")
    public String test() {
        return "ok";
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String saveOrderFallback(long s) {
        return String.format("saveOrderFallback %d", s);
    }

    // BlockHandle 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String saveOrderExHandler(long s, BlockException ex) {
        return "Oops, error occurred at saveOrder" + s;
    }

}
