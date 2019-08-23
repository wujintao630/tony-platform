package com.tonytaotao.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new MainTask());
        ExecutorService executorService = Executors.newCachedThreadPool();

        SubTask subTask1 = new SubTask("A", cyclicBarrier);
        SubTask subTask2 = new SubTask("B", cyclicBarrier);
        SubTask subTask3 = new SubTask("C", cyclicBarrier);
        SubTask subTask4 = new SubTask("D", cyclicBarrier);
        SubTask subTask5 = new SubTask("E", cyclicBarrier);

        executorService.submit(subTask1);
        executorService.submit(subTask2);
        executorService.submit(subTask3);
        executorService.submit(subTask4);
        executorService.submit(subTask5);

        executorService.shutdown();

        System.out.println("主程序结束");
    }

}

class MainTask implements Runnable {
    @Override
    public void run() {
        System.out.println("主任务执行完成");
    }
}

class SubTask implements Runnable {

    private String taskName;

    private CyclicBarrier cyclicBarrier;

    public SubTask(String taskName, CyclicBarrier cyclicBarrier) {
        this.taskName = taskName;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.println("子任务" + taskName +"开始执行");
            Thread.sleep(2000);
            System.out.println("子任务" + taskName +"执行完成");
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}


