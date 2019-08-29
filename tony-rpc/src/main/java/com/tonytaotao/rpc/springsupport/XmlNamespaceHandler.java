package com.tonytaotao.rpc.springsupport;

import com.tonytaotao.rpc.config.ApplicationConfig;
import com.tonytaotao.rpc.config.ProtocolConfig;
import com.tonytaotao.rpc.config.RegistryConfig;
import com.tonytaotao.rpc.util.ConcurrentHashSet;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import java.util.Set;

public class XmlNamespaceHandler extends NamespaceHandlerSupport {
    public final static Set<String> protocolDefineNames = new ConcurrentHashSet<String>();
    public final static Set<String> registryDefineNames = new ConcurrentHashSet<String>();
    public final static Set<String> serviceConfigDefineNames = new ConcurrentHashSet<String>();
    public final static Set<String> referenceConfigDefineNames = new ConcurrentHashSet<String>();
    public final static Set<String> applicationConfigDefineNames = new ConcurrentHashSet<String>();

    @Override
    public void init() {
        registerBeanDefinitionParser("reference", new XmlBeanDefinitionParser(ReferenceConfigBean.class, false));
        registerBeanDefinitionParser("service", new XmlBeanDefinitionParser(ServiceConfigBean.class, true));
        registerBeanDefinitionParser("registry", new XmlBeanDefinitionParser(RegistryConfig.class, true));
        registerBeanDefinitionParser("protocol", new XmlBeanDefinitionParser(ProtocolConfig.class, true));
        registerBeanDefinitionParser("application", new XmlBeanDefinitionParser(ApplicationConfig.class, true));
    }
}
