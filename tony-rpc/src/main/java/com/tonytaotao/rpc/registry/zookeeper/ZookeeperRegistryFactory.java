package com.tonytaotao.rpc.registry.zookeeper;

import com.tonytaotao.rpc.common.URL;
import com.tonytaotao.rpc.common.URLParam;
import com.tonytaotao.rpc.registry.AbstractRegistryFactory;
import com.tonytaotao.rpc.registry.Registry;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;

public class ZookeeperRegistryFactory extends AbstractRegistryFactory {

    @Override
    protected Registry createRegistry(URL registryUrl) {
        try {
            int timeout = registryUrl.getIntParameter(URLParam.registryConnectTimeout.getName(), URLParam.registryConnectTimeout.getIntValue());
            int sessionTimeout =
                    registryUrl.getIntParameter(URLParam.registrySessionTimeout.getName(),
                            URLParam.registrySessionTimeout.getIntValue());
            ZkClient zkClient = new ZkClient(registryUrl.getParameter(URLParam.registryAddress.getName()), sessionTimeout, timeout);
            return new ZookeeperRegistry(registryUrl, zkClient);
        } catch (ZkException e) {
            throw e;
        }
    }
}
