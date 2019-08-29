package com.tonytaotao.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ReferenceInvocationHandler<T> implements InvocationHandler {

    private Class<T> clazz;

    public ReferenceInvocationHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getDeclaringClass().equals(Object.class)) {
            if ("toString".equals(method.getName())) {
                return "";
            }
            throw new Exception("can not invoke local method:" + method.getName());
        }


        .....

        return null;
    }
}
