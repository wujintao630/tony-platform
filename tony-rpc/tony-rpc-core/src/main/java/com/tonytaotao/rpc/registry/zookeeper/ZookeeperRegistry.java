package com.tonytaotao.rpc.registry.zookeeper;

import com.tonytaotao.rpc.common.URL;
import com.tonytaotao.rpc.common.UrlParamEnum;
import com.tonytaotao.rpc.common.exception.FrameworkRpcException;
import com.tonytaotao.rpc.registry.ServiceCommon;
import com.tonytaotao.rpc.registry.SpiRegistry;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * zookeeper注册中心
 * @author tony
 */
public class ZookeeperRegistry implements SpiRegistry {

    private final ConcurrentHashMap<String, ServiceCommon> registriedServiceMap= new ConcurrentHashMap<String, ServiceCommon>();
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public ServiceCommon getRegistry(URL url) {
        String registryUri = url.getUri();
        try {
            lock.lock();
            ServiceCommon registry = registriedServiceMap.get(registryUri);
            if (registry != null) {
                return registry;
            }
            registry = createRegistry(url);
            if (registry == null) {
                throw new FrameworkRpcException("Create registry false for url:" + url);
            }
            registriedServiceMap.put(registryUri, registry);
            return registry;
        } catch (Exception e) {
            throw new FrameworkRpcException("Create registry false for url:" + url, e);
        } finally {
            lock.unlock();
        }
    }


    private ServiceCommon createRegistry(URL registryUrl) {
        try {
            int connecttTimeout = registryUrl.getIntParameter(UrlParamEnum.registryConnectTimeout.getName(), UrlParamEnum.registryConnectTimeout.getIntValue());
            int sessionTimeout = registryUrl.getIntParameter(UrlParamEnum.registrySessionTimeout.getName(), UrlParamEnum.registrySessionTimeout.getIntValue());
            ZkClient zkClient = new ZkClient(registryUrl.getParameter(UrlParamEnum.registryAddress.getName()), sessionTimeout, connecttTimeout);
            return new ZookeeperRegistryFactory(registryUrl, zkClient);
        } catch (ZkException e) {
            throw e;
        }
    }
}
