package com.tonytaotao.rpc.serializer.defaults;

import com.tonytaotao.rpc.serializer.Serializer;

import java.io.IOException;

public class DefaultSerializer implements Serializer {

    @Override
    public byte[] serialize(Object msg) throws IOException {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> type) throws IOException {
        return null;
    }
}