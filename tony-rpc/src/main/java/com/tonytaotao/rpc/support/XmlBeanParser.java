package com.tonytaotao.rpc.support;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class XmlBeanParser implements BeanDefinitionParser {

    private final Class<?> clazz;

    private final boolean required;

    public XmlBeanParser(Class<?> clazz, boolean required) {
        this.clazz = clazz;
        this.required = required;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        try {
            return parse(element, parserContext, clazz, required);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private BeanDefinition parse(Element element, ParserContext parserContext, Class<?> clazz, boolean required)  throws ClassNotFoundException{

        // 待注册的bean
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(clazz);
        beanDefinition.setLazyInit(false);

        String id = element.getAttribute("id");

        // 一定要有该bean，但是没有定义id
        if (StringUtils.isBlank(id) && required) {
            String beanName = element.getAttribute("name");
            if (StringUtils.isNotBlank(beanName)) {
                beanName = element.getAttribute("interface");
                if (StringUtils.isNotBlank(beanName)) {
                    beanName = clazz.getName();
                }
            }

            id = beanName;

            // 如果该名字已经注册了，则需要循环更改名字，直到不重复为止
            int count = 2;
            while (parserContext.getRegistry().containsBeanDefinition(id)) {
                id = beanName + (count++);
            }
        }

        // 已经定义了id
        if (StringUtils.isNotBlank(id)) {
            // 检查bean是否已注册
            if (parserContext.getRegistry().containsBeanDefinition(id)) {
                throw new IllegalStateException("Duplicate Spring Bean ID " + id);
            }
            // 注册bean
            parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        }

        // bean绑定id属性
        beanDefinition.getPropertyValues().addPropertyValue("id", id);


        // 根据不同的bean解析对应的属性
        if (ApplicationConfig.class.equals(clazz)) {
            XmlNamespaceHandler.applicationList.add(id);
            parseCommonProperty("name", null, element, beanDefinition);

        } else if (ProtocolConfig.class.equals(clazz)) {
            XmlNamespaceHandler.protocolList.add(id);
            parseCommonProperty("name", null, element, beanDefinition);
            parseCommonProperty("host", null, element, beanDefinition);
            parseCommonProperty("port", null, element, beanDefinition);

        } else if (RegistryConfig.class.equals(clazz)) {
            XmlNamespaceHandler.registryList.add(id);
            parseCommonProperty("protocol", null, element, beanDefinition);
            parseCommonProperty("address", null, element, beanDefinition);

         } else if (ServiceConfig.class.equals(clazz)) {
            XmlNamespaceHandler.serviceList.add(id);
            // 别名的原因是 interface 是java关键字，不能被定义为类的属性名，所以采用别名
            parseCommonProperty("interface", "interfaceName", element, beanDefinition);
            parseBeanRef("ref", element, beanDefinition, parserContext);

        } else if (ReferenceConfig.class.equals(clazz)) {
            XmlNamespaceHandler.referenceList.add(id);
            parseCommonProperty("interface", null, element, beanDefinition);
        }

        return beanDefinition;

    }

    private void parseCommonProperty(String propertyName , String alias, Element element, BeanDefinition beanDefinition) {
        String value = element.getAttribute(propertyName);
        if (StringUtils.isNotBlank(value)) {
            propertyName = alias == null? propertyName : alias;
            beanDefinition.getPropertyValues().addPropertyValue(propertyName, value);
        }
    }

    private void parseBeanRef(String propertyName, Element element, BeanDefinition beanDefinition, ParserContext parserContext) {
        String value = element.getAttribute(propertyName);
        if (StringUtils.isNotBlank(value)) {
            if (parserContext.getRegistry().containsBeanDefinition(value)) {
                BeanDefinition refBean = parserContext.getRegistry().getBeanDefinition(value);
                if (!refBean.isSingleton()) {
                    throw new IllegalStateException("The xml bean ref " + value + " must be singleton! Please set the " + value + " bean to singleton use property scope.");
                }
            }
            beanDefinition.getPropertyValues().addPropertyValue(propertyName, new RuntimeBeanReference(value));
        }

    }
}
