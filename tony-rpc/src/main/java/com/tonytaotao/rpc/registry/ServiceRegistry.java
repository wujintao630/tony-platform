package com.tonytaotao.rpc.registry;

import com.tonytaotao.rpc.core.URL;

public interface ServiceRegistry {

    /**
     * 注册服务
     * @param url
     * @throws Exception
     */
    void register(URL url) throws Exception;

    /**
     * 取消注册服务
     * @param url
     * @throws Exception
     */
    void unregister(URL url) throws Exception;

}
