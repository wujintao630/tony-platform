package com.tonytaotao.rpc.filter;

import com.tonytaotao.rpc.core.Request;
import com.tonytaotao.rpc.core.Response;
import com.tonytaotao.rpc.core.extension.SPI;
import com.tonytaotao.rpc.rpc.Caller;

@SPI
public interface Filter {

    Response filter(Caller<?> caller, Request request);

}
