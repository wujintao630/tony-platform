package com.tonytaotao.rpc.registry;

import com.tonytaotao.rpc.common.URL;

public interface Registry extends RegistryService, DiscoveryService {

    URL getUrl();

    void close();
}
