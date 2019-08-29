package com.tonytaotao.rpc.support;

import java.io.Serializable;

public class XmlAbstractBeanConfig implements Serializable {

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
