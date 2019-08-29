package com.tonytaotao.rpc.codec;

import com.tonytaotao.rpc.message.DefaultRequest;
import com.tonytaotao.rpc.message.DefaultResponse;
import com.tonytaotao.rpc.serializer.KryoSerializer;

import java.io.IOException;

public class Codec {

    public static byte[] encode(String message) throws IOException {

        if (message != null) {
            return  null;
        }

        return KryoSerializer.serialize(message);

    }

    public static Object decode(byte messageType, byte[] data) throws IOException {

        if (messageType == 0x01) {
            return KryoSerializer.deserialize(data, DefaultRequest.class);
        }
        return KryoSerializer.deserialize(data, DefaultResponse.class);

    }

}
