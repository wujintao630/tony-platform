package com.tonytaotao.springboot.dubbo.account.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.tonytaotao.springboot.dubbo.account.account.entity.UserAccount;
import com.tonytaotao.springboot.dubbo.account.account.service.UserAccountService;
import com.tonytaotao.springboot.dubbo.api.ApiAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *不能使用spring的@Service注解，而要用Dubbo提供的@Service注解
 */
@Service(interfaceClass = ApiAccountService.class, version ="1.0.0",  timeout = 10000)
@Component
public class ApiAccountServiceImpl implements ApiAccountService {

    @Autowired
    private UserAccountService userAccountService;

    @Override
    @LcnTransaction
    @Transactional
    public boolean subAccountBalance(Long userId, Double money) {

        UserAccount userAccount = userAccountService.getOne(new QueryWrapper<>(new UserAccount().setUserId(userId)));
        if (userAccount == null) {
            return false;
        }

        userAccount.setBalance(userAccount.getBalance() - money);

        return userAccountService.updateById(userAccount);
    }
}
