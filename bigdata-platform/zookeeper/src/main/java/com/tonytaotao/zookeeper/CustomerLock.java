package com.tonytaotao.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;

public class CustomerLock {


    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    public void getLock() {
        CuratorFramework client = ZkClientUtil.build();
        client.start();

        while (true) {
            try {
                // 创建序列节点
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/distribute_lock/lock");
                // 判断当前节点是否是最小序列节点
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
