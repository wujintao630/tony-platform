package com.tonytaotao.rpc.core;

import java.util.List;

public interface NotifyListener {

    void notify(URL registryUrl, List<URL> urlList);

}
