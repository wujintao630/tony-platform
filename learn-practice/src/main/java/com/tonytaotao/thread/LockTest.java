package com.tonytaotao.thread;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.*;

public class LockTest {

    public static void main(String[] args) {
        //operateBankCard();
        //operateBankCardWithRead();
        operateBankCardCondition();
    }

    /**
     * 普通锁
     */
    public static void operateBankCard() {

        MyBankCard myBankCard =  new MyBankCard("622848000000000", 1000);
        Lock lock = new ReentrantLock();

        OperateBankCardRunnable user1 = new OperateBankCardRunnable("张三", myBankCard, -100, lock);
        OperateBankCardRunnable user2 = new OperateBankCardRunnable("李四", myBankCard, +50, lock);
        OperateBankCardRunnable user3 = new OperateBankCardRunnable("王五", myBankCard, -200, lock);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(user1);
        executorService.submit(user2);
        executorService.submit(user3);

        executorService.shutdown();

    }

    /**
     * 读写锁
     */
    public static void operateBankCardWithRead() {
        MyBankCard myBankCard =  new MyBankCard("622848000000000", 1000);
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        OperateBankCardRunnableWithRead user1 = new OperateBankCardRunnableWithRead("张三", myBankCard, -100, false, readWriteLock);
        OperateBankCardRunnableWithRead user2 = new OperateBankCardRunnableWithRead("李四", myBankCard, +50, true, readWriteLock);
        OperateBankCardRunnableWithRead user3 = new OperateBankCardRunnableWithRead("王五", myBankCard, -200, false, readWriteLock);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(user1);
        executorService.submit(user2);
        executorService.submit(user3);

        executorService.shutdown();
    }

    /**
     * 条件锁
     */
    public static void operateBankCardCondition() {

        MyBankCard myBankCard =  new MyBankCard("622848000000000", 1000);

        GetOrPutMoney user1 = new GetOrPutMoney("张三", 100, false, myBankCard);
        GetOrPutMoney user2 = new GetOrPutMoney("李四", 50, true, myBankCard);
        GetOrPutMoney user3 = new GetOrPutMoney("王五", 1100, true, myBankCard);
        GetOrPutMoney user4 = new GetOrPutMoney("朱六", 200, false, myBankCard);
        GetOrPutMoney user5 = new GetOrPutMoney("吴七", 900, false, myBankCard);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(user1);
        executorService.submit(user2);
        executorService.submit(user3);
        executorService.submit(user4);
        executorService.submit(user5);

        executorService.shutdown();

    }
}

class OperateBankCardRunnable implements Runnable {

    private String userName;

    private MyBankCard myBankCard;

    private int money;

    private Lock lock;

    public OperateBankCardRunnable(String userName, MyBankCard myBankCard, int money, Lock lock) {
        this.userName = userName;
        this.myBankCard = myBankCard;
        this.money = money;
        this.lock = lock;
    }

    @Override
    public void run() {

        try {
            lock.lock();
            System.out.println( "用户" + userName + "正在操作" + myBankCard.getCardNo() +"账户, 当前余额为" + myBankCard.getCardAccount() + ", 操作金额为" + money);
            myBankCard.setCardAccount(myBankCard.getCardAccount() + money);
            System.out.println( "用户" + userName + "操作" + myBankCard.getCardNo() +"账户成功, 当前余额为" + myBankCard.getCardAccount() + ", 已操作金额为" + money);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


class OperateBankCardRunnableWithRead implements Runnable {

    private String userName;

    private MyBankCard myBankCard;

    private int money;

    private  boolean readBoo;

    private ReadWriteLock readWriteLock;

    public OperateBankCardRunnableWithRead(String userName, MyBankCard myBankCard, int money, boolean readBoo, ReadWriteLock readWriteLock) {
        this.userName = userName;
        this.myBankCard = myBankCard;
        this.money = money;
        this.readBoo = readBoo;
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void run() {

        if (readBoo) {

            try {
                readWriteLock.readLock().lock();
                System.out.println( "用户" + userName + "正在查询" + myBankCard.getCardNo() +"账户, 当前余额为" + myBankCard.getCardAccount());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                readWriteLock.readLock().unlock();
            }

        } else {

            try {
                readWriteLock.writeLock().lock();
                System.out.println( "用户" + userName + "正在操作" + myBankCard.getCardNo() +"账户, 当前余额为" + myBankCard.getCardAccount() + ", 操作金额为" + money);
                myBankCard.setCardAccount(myBankCard.getCardAccount() + money);
                System.out.println( "用户" + userName + "操作" + myBankCard.getCardNo() +"账户成功, 当前余额为" + myBankCard.getCardAccount() + ", 已操作金额为" + money);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }

    }
}


class GetOrPutMoney implements Runnable {

    private String userName;

    private int money;

    private boolean getMoneyBoo;

    private MyBankCard myBankCard;

    public GetOrPutMoney(String userName, int money, boolean getMoneyBoo, MyBankCard myBankCard) {
        this.userName = userName;
        this.money = money;
        this.getMoneyBoo = getMoneyBoo;
        this.myBankCard = myBankCard;
    }

    @Override
    public void run() {
        if(getMoneyBoo) {
            myBankCard.getMoney(userName, money);
        } else {
            myBankCard.putMoney(userName, money);
        }
    }
}


class MyBankCard implements Serializable {

    private String cardNo;

    private int cardAccount;

    private Lock lock = new ReentrantLock();
    private Condition getBoo = lock.newCondition();
    private Condition putBoo = lock.newCondition();

    public MyBankCard(String cardNo, int cardAccount) {
        this.cardNo = cardNo;
        this.cardAccount = cardAccount;
    }


    public void getMoney(String userName, int money) {
        try {
            lock.lock();
            if (cardAccount - money < 0) {
                getBoo.await();
            }
            System.out.print("当前余额为" + cardAccount + ",");
            cardAccount = cardAccount - money;
            System.out.println("用户" + userName +"取款" + money + ", 余额为" + cardAccount);
            putBoo.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void putMoney(String userName, int money) {
        try {
            lock.lock();
            if (money > 0) {
                System.out.print("当前余额为" + cardAccount + ",");
                cardAccount = cardAccount + money;
                System.out.println("用户" + userName +"存款" + money + ", 余额为" + cardAccount);
            }
            getBoo.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }



    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(int cardAccount) {
        this.cardAccount = cardAccount;
    }
}