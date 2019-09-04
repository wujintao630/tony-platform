package com.tonytaotao.rpc.registry;

import com.tonytaotao.rpc.common.URL;

public interface Registry extends ServiceRegistry, ServiceDiscovery {

    URL getUrl();

    void close();
}
