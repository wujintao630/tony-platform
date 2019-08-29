package com.tonytaotao.rpc.transport;

import com.tonytaotao.rpc.core.Request;
import com.tonytaotao.rpc.core.Response;
import com.tonytaotao.rpc.core.ResponseFuture;
import com.tonytaotao.rpc.exception.TransportException;

public interface NettyClient extends Endpoint {

    Response invokeSync(final Request request)
            throws InterruptedException, TransportException;

    ResponseFuture invokeAsync(final Request request)
            throws InterruptedException, TransportException;

    void invokeOneway(final Request request)
            throws InterruptedException, TransportException;

}
