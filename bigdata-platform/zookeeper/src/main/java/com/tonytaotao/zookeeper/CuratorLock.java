package com.tonytaotao.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorLock {



    /**
     * 使用curator实现的分布式锁
     */
    public void curatorLock() {

        CuratorFramework client1 = ZkClientUtil.build();
        client1.start();

        CuratorFramework client2 = ZkClientUtil.build();
        client2.start();

        // 创建分布式锁
        InterProcessMutex mutex1 = new InterProcessMutex(client1, "/distribute_lock");
        InterProcessMutex mutex2 = new InterProcessMutex(client2, "/distribute_lock");

        ExecutorService executorService = Executors.newCachedThreadPool();

        Runnable thread1 = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        mutex1.acquire();
                        System.out.println("client1 get lock");
                        Thread.sleep(500);
                        mutex1.release();
                        System.out.println("client1 release lock");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable thread2 = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        mutex2.acquire();
                        System.out.println("client2 get lock");
                        Thread.sleep(500);
                        mutex2.release();
                        System.out.println("client2 release lock");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        executorService.submit(thread1);
        executorService.submit(thread2);

    }

}
