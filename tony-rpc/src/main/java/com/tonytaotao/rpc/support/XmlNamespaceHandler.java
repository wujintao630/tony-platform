package com.tonytaotao.rpc.support;

import com.tonytaotao.rpc.utils.ConcurrentHashSet;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import java.util.Set;

public class XmlNamespaceHandler extends NamespaceHandlerSupport {

    public final static Set<String> protocolList = new ConcurrentHashSet<>();

    public final static Set<String> applicationList = new ConcurrentHashSet<>();

    public final static Set<String> registryList = new ConcurrentHashSet<>();

    public final static Set<String> serviceList = new ConcurrentHashSet<>();

    public final static Set<String> referenceList = new ConcurrentHashSet<>();

    @Override
    public void init() {

        registerBeanDefinitionParser("protocol", new XmlBeanParser(ProtocolConfig.class, true));
        registerBeanDefinitionParser("application", new XmlBeanParser(ApplicationConfig.class, true));
        registerBeanDefinitionParser("registry", new XmlBeanParser(RegistryConfig.class, true));
        registerBeanDefinitionParser("service", new XmlBeanParser(ServiceConfig.class, true));
        registerBeanDefinitionParser("reference", new XmlBeanParser(ReferenceConfig.class, false));

    }
}
