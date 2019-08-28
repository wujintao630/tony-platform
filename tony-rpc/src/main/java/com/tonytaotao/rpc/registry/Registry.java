package com.tonytaotao.rpc.registry;

import com.tonytaotao.rpc.core.URL;

public interface Registry extends ServiceRegistry, ServiceDiscover{

    URL getUrl() throws Exception;

    void close() throws Exception;

}
