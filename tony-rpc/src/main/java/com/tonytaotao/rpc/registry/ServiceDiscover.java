package com.tonytaotao.rpc.registry;

import com.tonytaotao.rpc.core.NotifyListener;
import com.tonytaotao.rpc.core.URL;

import java.util.List;

public interface ServiceDiscover {

    /**
     * 订阅服务
     * @param url
     * @param notifyListener
     * @throws Exception
     */
    void subscribe(URL url, NotifyListener notifyListener) throws Exception;

    /**
     * 取消订阅服务
     * @param url
     * @param notifyListener
     * @throws Exception
     */
    void unsubscribe(URL url, NotifyListener notifyListener) throws Exception;

    /**
     * 查询已注册的服务
     * @param url
     * @return
     * @throws Exception
     */
    List<URL> discover(URL url) throws Exception;

}
