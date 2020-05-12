package com.tonytaotao.springboot.dubbo.common.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MyServletOutputStream extends ServletOutputStream {

    private ByteArrayOutputStream bos = null;

    public MyServletOutputStream(ByteArrayOutputStream stream) throws IOException {
        bos = stream;
    }

    @Override
    public void write(int b) throws IOException {
        bos.write(b);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener listener) {

    }
}
