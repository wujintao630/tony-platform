package com.tonytaotao.rpc.rpc;

import com.tonytaotao.rpc.core.Request;
import com.tonytaotao.rpc.core.Response;

public interface Caller<T> extends Node {

    Class<T> getInterface();

    Response call(Request request);

}
