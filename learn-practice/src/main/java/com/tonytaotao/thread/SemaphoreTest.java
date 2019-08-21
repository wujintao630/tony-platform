package com.tonytaotao.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreTest {

    public static void main(String[] args) {
        ResoureManager resoureManager = new ResoureManager();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new ResourceUser(resoureManager, "tony-" + i));
            threads[i] = thread;
        }

        for (int i = 0 ; i < 100; i++) {
            try {
                threads[i].start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


class ResoureManager{

    private final Semaphore semaphore;
    private boolean resourceArray[];
    private final ReentrantLock reentrantLock;

    public ResoureManager() {

        this.resourceArray = new boolean[10];
        this.semaphore = new Semaphore(10, true);
        this.reentrantLock = new ReentrantLock(true);

        for (int i = 0; i < 10; i++) {
            resourceArray[i] = true;
        }
    }

    // 用户想使用资源
    public void useResource(String userName) {
        try {
            semaphore.acquire();
            int id = getResourceId();
            if (id < 0) {
                System.out.println("用户：" + userName  + "无资源可用");
            } else {
                System.out.println("用户：" + userName  + "正在使用" + id + "号资源");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    // 找到一个可用的资源
    private int getResourceId() {
        int id = -1;
        try {
            reentrantLock.lock();
            for (int i = 0; i < 10; i ++) {
                if (resourceArray[i] == true) {
                    resourceArray[i] = false;
                    id = i;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
        return id;
    }
}

class ResourceUser implements Runnable {

    private ResoureManager resoureManager;
    private String userName;

    public ResourceUser(ResoureManager resoureManager, String userName) {
        this.resoureManager = resoureManager;
        this.userName = userName;
    }

    @Override
    public void run() {
        System.out.println("用户：" + userName  + "准备使用资源");
        resoureManager.useResource(userName);
        System.out.println("用户：" + userName  + "使用资源完毕");
    }
}