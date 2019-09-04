package com.tonytaotao.rpc.springsupport;

import com.tonytaotao.rpc.common.URLParam;
import com.tonytaotao.rpc.config.ProtocolConfig;
import com.tonytaotao.rpc.config.ReferenceConfig;
import com.tonytaotao.rpc.config.RegistryConfig;
import com.tonytaotao.rpc.util.FrameworkUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

public class ReferenceConfigBean<T> extends ReferenceConfig<T> implements
        FactoryBean<T>, BeanFactoryAware,
        InitializingBean, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private transient BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public T getObject() throws Exception {
        return get();
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

        logger.debug("check reference interface:%s config", getInterfaceName());
        //检查依赖的配置
        checkApplication();
        checkProtocolConfig();
        checkRegistryConfig();

        if(StringUtils.isEmpty(getGroup())) {
            setGroup(URLParam.group.getValue());
        }
        if(StringUtils.isEmpty(getVersion())) {
            setVersion(URLParam.version.getValue());
        }

        if(getTimeout()==null) {
            setTimeout(URLParam.requestTimeout.getIntValue());
        }
        if(getRetries()==null) {
            setRetries(URLParam.retries.getIntValue());
        }
    }

    @Override
    public void destroy() throws Exception {
        super.destroy0();
    }

    private void checkRegistryConfig() {
        if (CollectionUtils.isEmpty(getRegistries())) {
            for (String name : XmlNamespaceHandler.registryDefineNames) {
                RegistryConfig rc = beanFactory.getBean(name, RegistryConfig.class);
                if (rc == null) {
                    continue;
                }
                if (XmlNamespaceHandler.registryDefineNames.size() == 1) {
                    setRegistry(rc);
                } else if (rc.isDefault() != null && rc.isDefault().booleanValue()) {
                    setRegistry(rc);
                }
            }
        }
        if (CollectionUtils.isEmpty(getRegistries())) {
            setRegistry(FrameworkUtils.getDefaultRegistryConfig());
        }
    }

    private void checkProtocolConfig() {
        if (CollectionUtils.isEmpty(getProtocols())) {
            for (String name : XmlNamespaceHandler.protocolDefineNames) {
                ProtocolConfig pc = beanFactory.getBean(name, ProtocolConfig.class);
                if (pc == null) {
                    continue;
                }
                if (XmlNamespaceHandler.protocolDefineNames.size() == 1) {
                    setProtocol(pc);
                } else if (pc.isDefault() != null && pc.isDefault().booleanValue()) {
                    setProtocol(pc);
                }
            }
        }
        if (CollectionUtils.isEmpty(getProtocols())) {
            setProtocol(FrameworkUtils.getDefaultProtocolConfig());
        }
    }
}
