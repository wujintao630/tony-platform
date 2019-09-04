package com.tonytaotao.rpc.registry;

import com.tonytaotao.rpc.common.URL;
import com.tonytaotao.rpc.core.extension.SPI;
import com.tonytaotao.rpc.core.extension.Scope;

@SPI(scope = Scope.SINGLETON)
public interface RegistryFactory {

    Registry getRegistry(URL url);
}
