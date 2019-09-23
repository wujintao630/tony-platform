package com.tonytaotao.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkClientUtil {

    public static CuratorFramework build() {

        // 重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(5000, 3);

        CuratorFramework client = CuratorFrameworkFactory.builder()
                                                         .connectString("192.168.56.101:2181,192.168.56.101:2182,192.168.56.101:2183")
                                                         .retryPolicy(retryPolicy)
                                                         .namespace("zk_curator")
                                                         .sessionTimeoutMs(10000)
                                                         .build();

        return  client;

    }

}
