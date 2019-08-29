package com.tonytaotao.rpc.rpc;

import com.tonytaotao.rpc.core.Request;
import com.tonytaotao.rpc.core.Response;

public interface MessageHandler {

    Response handle(Request request);

}
