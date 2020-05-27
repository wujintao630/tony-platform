package com.tonytaotao.springboot.dubbo.api;

/**
 * RPC 账户服务
 * @author tonytaotao
 */
public interface ApiAccountService {

    /**
     * 扣减账户余额
     * @param userId
     * @param money
     * @return
     */
    boolean subAccountBalance(Long userId, Double money);

}
