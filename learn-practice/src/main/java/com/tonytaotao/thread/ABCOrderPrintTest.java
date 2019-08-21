package com.tonytaotao.thread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ABCOrderPrintTest {

    private Lock lock = new ReentrantLock();// 通过JDK5中的锁来保证线程的访问的互斥
    private Condition condition = lock.newCondition();// 线程协作

    public static void main(String[] args) {
        ABCOrderPrintTest abc = new ABCOrderPrintTest();

        // 使用循环可以不局限于只有3个线程 交替, 任意多个都可以.
        PrintThread a = abc.new PrintThread("A", true);
        PrintThread b = abc.new PrintThread("B", false);
        PrintThread c = abc.new PrintThread("C", false);

        a.setNext(b);
        b.setNext(c);
        c.setNext(a);

        ExecutorService executor = Executors.newFixedThreadPool(3);// 通过线程池执行
        for (int i = 0; i < 10; i++) {
            executor.execute(a);
            executor.execute(b);
            executor.execute(c);
        }
        executor.shutdown();//关闭线程池
    }

    class PrintThread implements Runnable {
        private String name;

        private PrintThread next;

        private boolean execute;

        public PrintThread(String name, boolean execute) {
            this.name = name;
            this.execute = execute;
        }

        public PrintThread(String name, PrintThread next, boolean execute) {
            this.name = name;
            this.next = next;
            this.execute = execute;
        }

        public void run() {
            lock.lock();
            try {
                while (true) {

                    if (execute) {
                        // 执行当前业务
                        print();
                        // 当前任务执行完成后 改变执行状态为false
                        execute = false;
                        // 将下一个相邻任务状态设置为true
                        next.setExecute(true);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 通知其它任务.
                        condition.signalAll();
                        break;
                    } else {
                        try {
                            // 若非执行状态 即等待.
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        }

        public void print() {
            System.out.println(name);
        }

        public void setExecute(boolean execute) {
            this.execute = execute;
        }

        public void setNext(PrintThread next) {
            this.next = next;
        }

    }

}
