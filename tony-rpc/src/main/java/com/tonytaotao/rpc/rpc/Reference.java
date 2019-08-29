package com.tonytaotao.rpc.rpc;

import com.tonytaotao.rpc.common.URL;

public interface Reference<T> extends Caller<T> {

    /**
     * 当前Reference的调用次数
     * @return
     */
    int activeCount();

    URL getServiceUrl();
}
