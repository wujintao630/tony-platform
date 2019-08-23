package com.tonytaotao.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolJDKTest {

    public static void main(String[] args) {
        //fixedThreadPool();
        //singleThreadPool();
        //cacheThreadPool();
        scheduleThreadPool();
    }

    /**
     * 固定大小线程池
     */
    public static void fixedThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        runThreadPool(executorService);
    }

    /**
     * 单一线程池
     */
    public static void singleThreadPool() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        runThreadPool(executorService);
    }

    /**
     * 可变线程池
     */
    public static void cacheThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        runThreadPool(executorService);
    }

    /**
     * 可调度线程池
     */
    public static void scheduleThreadPool() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        MyThread m1 = new MyThread();
        MyThread m2 = new MyThread();
        MyThread m3 = new MyThread();
        MyThread m4 = new MyThread();
        MyThread m5 = new MyThread();

        executorService.submit(m1);
        executorService.submit(m2);
        executorService.submit(m3);
        executorService.schedule(m4, 5, TimeUnit.SECONDS);
        executorService.schedule(m5, 5, TimeUnit.SECONDS);

        executorService.shutdown();
    }

    public static void runThreadPool(ExecutorService executorService) {

        MyThread m1 = new MyThread();
        MyThread m2 = new MyThread();
        MyThread m3 = new MyThread();
        MyThread m4 = new MyThread();
        MyThread m5 = new MyThread();

        executorService.submit(m1);
        executorService.submit(m2);
        executorService.submit(m3);
        executorService.submit(m4);
        executorService.submit(m5);

        executorService.shutdown();
    }
}


class MyThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "正在运行");
    }
}
