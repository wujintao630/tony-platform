package com.tonytaotao.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KryoSerializer implements Serializer{

    private static final ThreadLocal<Kryo> THREAD_LOCAL = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy());
            return kryo;
        }
    };

    @Override
    public byte[] serialize(Object msg) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        Kryo kryo = THREAD_LOCAL.get();
        kryo.writeObject(output, msg);
        return output.toBytes();

    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws IOException {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        Input input = new Input(bis);
        Kryo kryo = THREAD_LOCAL.get();
        return kryo.readObject(input, clazz);
    }
}
