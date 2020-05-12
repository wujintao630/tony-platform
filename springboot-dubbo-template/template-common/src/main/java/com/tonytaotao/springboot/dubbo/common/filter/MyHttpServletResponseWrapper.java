package com.tonytaotao.springboot.dubbo.common.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

public class MyHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream buffer = null;

    private ServletOutputStream out = null;

    private PrintWriter writer = null;


    public MyHttpServletResponseWrapper(HttpServletResponse response) throws IOException{
        super(response);

        buffer = new ByteArrayOutputStream();
        out = new MyServletOutputStream(buffer);
        writer = new PrintWriter(new OutputStreamWriter(buffer, "UTF-8"));
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
        if (writer != null) {
            writer.flush();
        }
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    public String getResponseData(String charset) throws IOException {
        flushBuffer();
        byte[] bytes = buffer.toByteArray();
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }

    }
}

