package com.tonytaotao.rpc.registry;

import com.tonytaotao.rpc.common.URL;

public interface ServiceRegistry {

    /**
     * register service to registry
     *
     * @param url
     */
    void register(URL url) throws Exception;

    /**
     * unregister service to registry
     *
     * @param url
     */
    void unregister(URL url) throws Exception;

}
