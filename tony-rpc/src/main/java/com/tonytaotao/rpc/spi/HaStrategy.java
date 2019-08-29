package com.tonytaotao.rpc.spi;

import com.tonytaotao.rpc.core.Request;
import com.tonytaotao.rpc.core.Response;
import com.tonytaotao.rpc.core.extension.SPI;
import com.tonytaotao.rpc.core.extension.Scope;

@SPI(scope = Scope.PROTOTYPE)
public interface HaStrategy<T> {

    Response call(Request request, LoadBalance loadBalance);
}
