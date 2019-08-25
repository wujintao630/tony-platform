package com.tonytaotao.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolTest {


    public static void main(String[] args) {

        //fixedThreadPool();
        //singleThreadPool();
        //cacheThreadPool();
        //scheduleThreadPool();

        //customThreadPoolRunnable();
        customThreadPoolcallable();

    }


    /*----------------------------系统线程池-----------------------------*/

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
        MyThreadRunnable m1 = new MyThreadRunnable();
        MyThreadRunnable m2 = new MyThreadRunnable();
        MyThreadRunnable m3 = new MyThreadRunnable();
        MyThreadRunnable m4 = new MyThreadRunnable();
        MyThreadRunnable m5 = new MyThreadRunnable();

        executorService.submit(m1);
        executorService.submit(m2);
        executorService.submit(m3);
        executorService.schedule(m4, 5, TimeUnit.SECONDS);
        executorService.schedule(m5, 5, TimeUnit.SECONDS);

        executorService.shutdown();
    }

    public static void runThreadPool(ExecutorService executorService) {

        MyThreadRunnable m1 = new MyThreadRunnable();
        MyThreadRunnable m2 = new MyThreadRunnable();
        MyThreadRunnable m3 = new MyThreadRunnable();
        MyThreadRunnable m4 = new MyThreadRunnable();
        MyThreadRunnable m5 = new MyThreadRunnable();

        executorService.submit(m1);
        executorService.submit(m2);
        executorService.submit(m3);
        executorService.submit(m4);
        executorService.submit(m5);

        executorService.shutdown();
    }



    /*----------------------------自定义线程池---------------------------*/


    /**
     * 核心线程数
     */
    private static final int POOL_CORE_SIZE = 10;

    /**
     * 最大线程数
     */
    private static final int POOL_MAX_SIZE = 20;

    /**
     * 超过核心线程数的线程存活时间
     */
    private static final int KEEP_ALIVE_TIME = 3000;

    /**
     * 线程工厂
     */
    private static ThreadFactory threadFactory;

   /**
     * 阻塞队列
     */
    private static BlockingQueue<Runnable> blockingDeque;

    /**
     * 阻塞队列满后的拒绝策略
     */
    private static RejectedExecutionHandler rejectedExecutionHandler;

    private static ExecutorService executorService;


    /**
     * 初始化自定义线程池
     */
    private static void initThreadPool() {
        blockingDeque = new ArrayBlockingQueue(100);
        threadFactory = new MyThreadFactory("tony");
        rejectedExecutionHandler = new MyRejectPolicy("tony");
        executorService = new ThreadPoolExecutor(POOL_CORE_SIZE, POOL_MAX_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, blockingDeque, threadFactory, rejectedExecutionHandler);

    }

    /**
     * 无返回值线程池测试
     */
    public static void customThreadPoolRunnable() {

        initThreadPool();

        for (int i  = 0; i < 10000 ; i++) {
            MyThreadRunnable myCustomThreadRunnable = new MyThreadRunnable();
            executorService.execute(myCustomThreadRunnable);
        }

        executorService.shutdown();
    }

    /**
     * 有返回值线程池测试
     */
    public static void customThreadPoolcallable() {

        initThreadPool();

        List<Future<String>> futureList = new ArrayList<>();

        for (int i  = 0; i < 100 ; i++) {
            MyThreadCallable myCustomThreadCallable = new MyThreadCallable();
            Future<String> future = executorService.submit(myCustomThreadCallable);
            futureList.add(future);
        }
        System.out.println("----------task submit finished--------------");

        futureList.forEach(future ->{
            try {
                System.out.println("future result is " + future.get() +", done is " + future.isDone());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

    }


}

/**
 * 自定义线程工厂
 */
class MyThreadFactory implements ThreadFactory {

    int count = 0;
    String threadName;

    public MyThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "Thread-" + threadName + "-" + count);
        count++;
        System.out.println(String.format("Created thread %d with name %s on%s\n", thread.getId(), thread.getName(), new Date()));
        return thread;
    }
}

/**
 * 自定义线程拒绝策略
 */
class MyRejectPolicy implements RejectedExecutionHandler {

    String threadName;

    public MyRejectPolicy(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        String msg = String.format("Current thread pool is exhausted ! threadName: %s, poolSize: %d (active: %d, core: %d, max: %d, largest: %d), task: %d (completed: %d), executorStatus: (isShutdown: %s, isTerminated: %s, isTerminating: %s)",
                                    threadName, executor.getPoolSize(), executor.getActiveCount(), executor.getCorePoolSize(), executor.getMaximumPoolSize(), executor.getLargestPoolSize(),
                                    executor.getTaskCount(), executor.getCompletedTaskCount(), executor.isShutdown(), executor.isTerminated(), executor.isTerminating());
        System.out.println(msg);

        throw new RejectedExecutionException(msg);
    }
}

/**
 * 无返回值线程
 */
class MyThreadRunnable implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("线程[" + Thread.currentThread().getName() + "]正在运行");
            Thread.sleep(1000);
            System.out.println("线程[" + Thread.currentThread().getName() + "]结束运行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 有返回值线程
 */
class MyThreadCallable implements Callable<String> {
    @Override
    public String call() throws Exception {

        Thread.sleep(1000);
        System.out.println("线程[" + Thread.currentThread().getName() + "]正在运行");
        Thread.sleep(1000);
        System.out.println("线程[" + Thread.currentThread().getName() + "]结束运行");

        return Thread.currentThread().getName() + "已完成任务";
    }
}
