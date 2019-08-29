package com.tonytaotao.rpc.rpc;

public interface Exporter<T> extends Node {

    Provider<T> getProvider();

    void unexport();
}
