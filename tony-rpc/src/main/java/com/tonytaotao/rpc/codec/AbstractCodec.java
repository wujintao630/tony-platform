package com.tonytaotao.rpc.codec;

import com.tonytaotao.rpc.spi.Codec;
import com.tonytaotao.rpc.spi.Serializer;

import java.io.IOException;

public abstract class AbstractCodec implements Codec {

    protected byte[] serialize(Object message, Serializer serializer) throws IOException {
        if (message == null) {
            return null;
        }
        return serializer.serialize(message);
    }

    protected Object deserialize(byte[] data, Class<?> type, Serializer serializer) throws IOException {
        if (data == null) {
            return null;
        }
        return serializer.deserialize(data, type);
    }
}
