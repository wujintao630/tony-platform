package com.tonytaotao.rpc.support;

import com.tonytaotao.rpc.core.URL;
import com.tonytaotao.rpc.registry.Registry;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class ReferenceConfig<T> extends XmlAbstractBeanConfig implements FactoryBean<T>, BeanFactoryAware, InitializingBean, DisposableBean {

    private String interfaceName;

    private Class<T> interfaceClass;

    private transient volatile T proxy;

    private transient volatile boolean initialized;

    private ApplicationConfig applicationConfig;

    private ProtocolConfig protocolConfig;

    private RegistryConfig registryConfig;

    private transient BeanFactory beanFactory;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void destroy() throws Exception {
        this.proxy = null;
    }

    @Override
    public T getObject() throws Exception {

        if (proxy == null) {
            init();
        }
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return getInterfaceClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        checkApplication();

        checkProtocolConfig();

        checkRegistryConfig();

    }

    private synchronized void init() {
        if (initialized) {
            return;
        }

        if (StringUtils.isBlank(interfaceName)) {
            throw new IllegalStateException("xml bean reference [interface] property can not be null");
        }

        try {
            interfaceClass = (Class<T>) Class.forName(interfaceName, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("reference class not found", e);
        }

        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException("reference interface is not interface");
        }

        List<URL> registryUrls = loadRegistryUrls();
        if (CollectionUtils.isEmpty(registryUrls)) {
            throw new IllegalStateException("you need set registry config:" + interfaceClass.getName());
        }

        this.proxy =

    }


    private List<URL> loadRegistryUrls() {
        List<URL> registryList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(registryList)) {

            String address = registryConfig.getAddress();
            String protocol = registryConfig.getProtocol();
            if (StringUtils.isBlank(address)) {
                address = "127.0.0.1:0";
                protocol = "local";
            }

            Map<String, String> map = new HashMap<>();
            map.put("application", StringUtils.isNotBlank(applicationConfig.getName()) ? applicationConfig.getName() : "");
            map.put("path", Registry.class.getName());
            map.put("registryAddress", String.valueOf(address));
            map.put("registryProtocol", String.valueOf(protocol));
            map.put("timestamp", String.valueOf(System.currentTimeMillis()));
            map.put("protocol", protocol);

            String[] array = address.split(":");
            URL url = new URL(protocol, array[0], Integer.parseInt(array[1]), Registry.class.getName(), map);
            registryList.add(url);
        }
        return registryList;
    }

    private void checkApplication() {
        if (applicationConfig == null) {
            applicationConfig = new ApplicationConfig();
        }

        if (applicationConfig == null) {
            throw new IllegalStateException("No such application config! Please add in xml file");
        }
        applicationConfig.setName(UUID.randomUUID().toString());
    }

    private void checkProtocolConfig() {
        if (protocolConfig == null) {
            for (String name : XmlNamespaceHandler.protocolList) {
                ProtocolConfig pc = beanFactory.getBean(name, ProtocolConfig.class);
                if (pc == null) {
                    continue;
                }
                if (XmlNamespaceHandler.protocolList.size() == 1) {
                    this.protocolConfig = pc;
                }
            }
        }

        if (protocolConfig == null) {
            // 设置默认协议
            protocolConfig = new ProtocolConfig();
            protocolConfig.setId("tonyrpc");
            protocolConfig.setName("tonyrpc");
            protocolConfig.setPort(19624);
        }
    }

    private void checkRegistryConfig() {
        if (registryConfig == null) {
            for (String name : XmlNamespaceHandler.registryList) {
                RegistryConfig rc = beanFactory.getBean(name, RegistryConfig.class);
                if (rc == null) {
                    continue;
                }
                if (XmlNamespaceHandler.registryList.size() == 1) {
                    this.registryConfig = rc;
                }
            }
        }

        if (registryConfig == null) {
            // 设置默认注册中心
            registryConfig = new RegistryConfig();
            registryConfig.setProtocol("local");
        }
    }


    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public T getProxy() {
        return proxy;
    }

    public void setProxy(T proxy) {
        this.proxy = proxy;
    }

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public ProtocolConfig getProtocolConfig() {
        return protocolConfig;
    }

    public void setProtocolConfig(ProtocolConfig protocolConfig) {
        this.protocolConfig = protocolConfig;
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }


}
