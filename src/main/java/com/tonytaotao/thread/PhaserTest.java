package com.tonytaotao.thread;

import java.util.Random;
import java.util.concurrent.Phaser;

public class PhaserTest {

    public static void main(String[] args) {

        MyPhaser myPhaser = new MyPhaser();
        myPhaser.register();

        for (int i = 0 ; i < 3 ; i++) {
            new Thread(new Runner("runner-" + i, myPhaser)).start();
        }

        myPhaser.arriveAndDeregister();

        while (!myPhaser.isTerminated()) {

        }

        System.out.println("比赛结束");

    }
}

class MyPhaser extends Phaser {

    private int endFlag = 2;

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {

        System.out.println("第" + phase + "阶段完成");
        return phase == endFlag || registeredParties == 0;
    }
}

class Runner implements Runnable {

    private String runnerName;

    private MyPhaser myPhaser;

    public Runner(String runnerName, MyPhaser myPhaser) {
        this.runnerName = runnerName;
        this.myPhaser = myPhaser;
        myPhaser.register();
    }

    @Override
    public void run() {

        try {
            Random rand = new Random();
            int randomNum = rand.nextInt((3000 - 1000) + 1) + 1000;
            Thread.sleep(randomNum);
            System.out.println("跑步者" + runnerName + "已到达赛场" + ", 耗时" + randomNum);
            myPhaser.arriveAndAwaitAdvance();

            randomNum = rand.nextInt((3000 - 1000) + 1) + 1000;
            Thread.sleep(randomNum);
            System.out.println("跑步者" + runnerName + "已准备好" + ", 耗时" + randomNum);
            myPhaser.arriveAndAwaitAdvance();

            randomNum = rand.nextInt((3000 - 1000) + 1) + 1000;
            Thread.sleep(randomNum);
            System.out.println("跑步者" + runnerName + "已完成比赛" + ", 耗时" + randomNum);
            myPhaser.arriveAndAwaitAdvance();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
