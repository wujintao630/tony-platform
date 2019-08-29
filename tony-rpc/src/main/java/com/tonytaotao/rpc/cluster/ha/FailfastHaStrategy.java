package com.tonytaotao.rpc.cluster.ha;


import com.tonytaotao.rpc.spi.HaStrategy;
import com.tonytaotao.rpc.spi.LoadBalance;
import com.tonytaotao.rpc.core.Request;
import com.tonytaotao.rpc.core.Response;
import com.tonytaotao.rpc.rpc.Reference;

public class FailfastHaStrategy<T> implements HaStrategy<T> {

    @Override
    public Response call(Request request, LoadBalance loadBalance) {
        Reference<T> reference = loadBalance.select(request);
        return reference.call(request);
    }
}
