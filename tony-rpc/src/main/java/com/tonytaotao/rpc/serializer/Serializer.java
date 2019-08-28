package com.tonytaotao.rpc.serializer;

import com.tonytaotao.rpc.core.SPI;

import java.io.IOException;

@SPI(value = "kryo")
public interface Serializer {

    byte[] serialize(Object msg) throws IOException;

    <T> T deserialize(byte[] data, Class<T> clazz) throws IOException;

}
