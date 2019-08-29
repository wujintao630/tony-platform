package com.tonytaotao.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JdkProxyFactory {

    public static  <T> T getProxy( Class<T> clazz, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, invocationHandler);
    }

}