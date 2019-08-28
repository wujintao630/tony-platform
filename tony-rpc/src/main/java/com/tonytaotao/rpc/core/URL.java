package com.tonytaotao.rpc.core;

import java.util.HashMap;
import java.util.Map;

public class URL {

    private String protocol;

    private String host;

    private int port;

    private String interfacePath;

    private Map<String, String> paramMap;

    public URL(String protocol, String host, int port, String interfacePath, Map<String, String> paramMap) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.interfacePath = interfacePath;
        this.paramMap = paramMap;
    }

    public URL(String protocol, String host, int port, String interfacePath) {
        this(protocol, host, port, interfacePath,  new HashMap<>());
    }

    public URL clone0() {
        Map<String, String> params = new HashMap<>();
        if (this.paramMap != null) {
            params.putAll(this.paramMap);
        }
        return new URL(protocol, host, port, interfacePath, params);
    }
}
