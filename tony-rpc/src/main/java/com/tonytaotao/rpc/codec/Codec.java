package com.tonytaotao.rpc.codec;

import com.tonytaotao.rpc.core.SPI;
import com.tonytaotao.rpc.core.URL;

import java.io.IOException;

@SPI("tonyCodec")
public interface Codec {

    byte[] encode(URL url, String message) throws IOException;

    Object decode(URL url, byte[] data) throws IOException;

}
