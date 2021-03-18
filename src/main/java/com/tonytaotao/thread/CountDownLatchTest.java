package com.tonytaotao.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        PlayerTask playerTask1 = new PlayerTask("A", countDownLatch);
        PlayerTask playerTask2 = new PlayerTask("B", countDownLatch);
        PlayerTask playerTask3 = new PlayerTask("C", countDownLatch);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(playerTask1);
        executorService.submit(playerTask2);
        executorService.submit(playerTask3);

        executorService.shutdown();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("游戏开始");
    }

}

class PlayerTask implements Runnable {

    private String playerName;

    private CountDownLatch countDownLatch;

    public PlayerTask(String playerName, CountDownLatch countDownLatch) {
        this.playerName = playerName;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        try {
            Random rand = new Random();
            int randomNum = rand.nextInt((3000 - 1000) + 1) + 1000;
            Thread.sleep(randomNum);
            System.out.println("玩家" + playerName +"已准备好, 耗时" + randomNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }

    }
}
