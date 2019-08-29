package com.tonytaotao.rpc.cluster;

import com.tonytaotao.rpc.core.extension.SPI;
import com.tonytaotao.rpc.core.extension.Scope;
import com.tonytaotao.rpc.rpc.Caller;
import com.tonytaotao.rpc.rpc.Reference;
import com.tonytaotao.rpc.spi.HaStrategy;
import com.tonytaotao.rpc.spi.LoadBalance;

import java.util.List;

@SPI(scope = Scope.PROTOTYPE)
public interface Cluster<T> extends Caller<T> {

    void setLoadBalance(LoadBalance<T> loadBalance);

    void setHaStrategy(HaStrategy<T> haStrategy);

    List<Reference<T>> getReferences();

    LoadBalance<T> getLoadBalance();
}
