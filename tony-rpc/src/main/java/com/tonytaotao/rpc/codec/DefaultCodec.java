package com.tonytaotao.rpc.codec;

import com.tonytaotao.rpc.common.URL;
import com.tonytaotao.rpc.common.URLParam;
import com.tonytaotao.rpc.core.DefaultRequest;
import com.tonytaotao.rpc.core.DefaultResponse;
import com.tonytaotao.rpc.core.extension.ExtensionLoader;
import com.tonytaotao.rpc.spi.Serializer;
import com.tonytaotao.rpc.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DefaultCodec extends AbstractCodec {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public byte[] encode(URL url, Object message) throws IOException {
        String serialization = url.getParameter(URLParam.serialization.getName(), URLParam.serialization.getValue());
        logger.info("Codec encode serialization:{}", serialization);
        return serialize(message, ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(serialization));
    }

    @Override
    public Object decode(URL url, byte messageType, byte[] data) throws IOException {
        String serialization = url.getParameter(URLParam.serialization.getName(), URLParam.serialization.getValue());
        logger.info("Codec decode serialization:{}", serialization);
        if(messageType == Constants.FLAG_REQUEST) {
            return deserialize(data, DefaultRequest.class, ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(serialization));
        }
        return deserialize(data, DefaultResponse.class, ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(serialization));
    }
}
