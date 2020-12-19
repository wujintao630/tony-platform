package com.tonytaotao.springboot.dubbo.webtools.controller;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @desc: Dubbo实例
 * @author: hbt
 * @date: 2020/08/27
 **/
@Slf4j
public class DubboReference {

    // 当前应用的信息
    private static ApplicationConfig application = new ApplicationConfig();

    // 注册中心信息缓存
    private static Map<String, RegistryConfig> registryConfigCache = new ConcurrentHashMap<>();

    // 各个业务方的ReferenceConfig缓存
    private static Map<String, ReferenceConfig> referenceCache = new ConcurrentHashMap<>();

    static {
        application.setName("dubbo-test");
    }

    /**
     * 获取注册中心信息
     *
     * @param address zk注册地址
     * @param group   dubbo服务所在的组
     * @return
     */
    private static RegistryConfig getRegistryConfig(String address, String group, String version) {
        String key = address + "-" + group + "-" + version;
        RegistryConfig registryConfig = registryConfigCache.get(key);
        if (null == registryConfig) {
            registryConfig = new RegistryConfig();
            if (StringUtils.isNotEmpty(address)) {
                registryConfig.setAddress(address);
            }
            if (StringUtils.isNotEmpty(version)) {
                registryConfig.setVersion(version);
            }
            if (StringUtils.isNotEmpty(group)) {
                registryConfig.setGroup(group);
            }
            registryConfigCache.put(key, registryConfig);
        }
        return registryConfig;
    }
    // 此实例很重，封装了与注册中心的连接以及与提供者的连接，需要缓存，否则可能造成内存和连接泄漏
    private static ReferenceConfig getReferenceConfig(String interfaceName, String address,
                                                      String group, String version, String requestIp) {

        String referenceKey = "";
        if (StringUtils.isNotEmpty(requestIp)) {
            referenceKey = interfaceName + "_" + address + "_" + group + "_" + version + "_" + requestIp;
        } else {
            referenceKey = interfaceName + "_" + address + "_" + group + "_" + version;
        }

        ReferenceConfig referenceConfig = referenceCache.get(referenceKey);

        try {
            if (referenceConfig == null) {
                referenceConfig = new ReferenceConfig<>();
                referenceConfig.setRegistry(getRegistryConfig(address, group, version));
                referenceConfig.setApplication(application);
                referenceConfig.setInterface(interfaceName);
                referenceConfig.setId(interfaceName);
                if (StringUtils.isNotEmpty(version)) {
                    referenceConfig.setVersion(version);
                }
                referenceConfig.setGeneric(true);
                if (StringUtils.isNotEmpty(requestIp)) {
                    referenceConfig.setUrl("dubbo://" + requestIp);
                }

                referenceCache.put(referenceKey, referenceConfig);
            }
        } catch (Exception e) {
            log.error("getReferenceConfig error", e);
        }

        return referenceConfig;
    }

    public static Object invoke(String interfaceName, String methodName, List<Object> paramList, String address, String version, String requestIp) {
        Assert.notNull(interfaceName, "interfaceName must not be null");
        Assert.notNull(methodName, "methodName must not be null");
        Assert.notNull(address, "address must not be null");
        Assert.notNull(version, "version must not be null");


        ReferenceConfig reference = getReferenceConfig(interfaceName, address, null, version, requestIp);
        if (null != reference) {

            GenericService genericService = (GenericService) reference.get();
            if (genericService == null) {
                log.info("GenericService 不存在:{}", interfaceName);
                return null;
            }
            Object[] paramObject = null;
            if (!CollectionUtils.isEmpty(paramList)) {
                paramObject = new Object[paramList.size()];
                for (int i = 0; i < paramList.size(); i++) {
                    paramObject[i] = paramList.get(i);
                }
            }

            Object resultParam = genericService.$invoke(methodName, null, paramObject);
            return resultParam;
        }
        return null;
    }


    public static String[] getMethodParamType(String interfaceName, String methodName) {
        try {
            //创建类
            Class<?> class1 = Class.forName(interfaceName);
            //获取所有的公共的方法
            Method[] methods = class1.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] paramClassList = method.getParameterTypes();
                    String[] paramTypeList = new String[paramClassList.length];
                    int i = 0;
                    for (Class className : paramClassList) {
                        paramTypeList[i] = className.getTypeName();
                        i++;
                    }
                    return paramTypeList;
                }
            }
        } catch (Exception e) {
            log.error("interfaceName={},getMethodParamType error", interfaceName, e);
        }
        return null;
    }
}
