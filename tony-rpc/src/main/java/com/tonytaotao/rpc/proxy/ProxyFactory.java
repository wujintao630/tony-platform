package com.tonytaotao.rpc.proxy;

import com.tonytaotao.rpc.core.SPI;

import java.lang.reflect.InvocationHandler;

@SPI("jdk")
public interface ProxyFactory {

    <T> T getProxy(Class<T> clazz, InvocationHandler invocationHandler);

}
