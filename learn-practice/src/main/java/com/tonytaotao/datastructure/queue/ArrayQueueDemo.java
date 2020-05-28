package com.tonytaotao.datastructure.queue;

import java.util.Scanner;

/**
 * 使用数组模拟队列
 */
public class ArrayQueueDemo {

    public static void main(String[] args) {
        ArrayQuque arrayQuque = new ArrayQuque(3);
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据");
            System.out.println("g(get):取数据");
            System.out.println("h(head):查看队列头数据");

            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    arrayQuque.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入数据");
                    int value = scanner.nextInt();
                    arrayQuque.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayQuque.getQueue();
                        System.out.printf("取出数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = arrayQuque.headQueue();
                        System.out.printf("队列头数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:break;
            }

        }

        System.out.println("程序退出");
    }
}

class ArrayQuque {
    /**
     * 数组最大容量
     */
    private int maxSize;

    /**
     * 队列头
     */
    private int front;

    /**
     * 队列尾
     */
    private int rear;

    /**
     * 存放数据，模拟队列
     */
    private int[] arr;

    public ArrayQuque(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
        front = -1; // 指向队列头部，队列头的第一个位置
        rear = -1; // 指向队列尾部，指向队列尾的数据（包含队列的最后一个数据）
    }

    public  boolean isFull() {
        return rear == maxSize - 1;
    }

    public boolean isEmpty() {
        return rear == front;
    }

    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("队列满，无法加入");
            return;
        }

        rear++;
        arr[rear] = n;
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，不能取数");
        }
        front++;
        return arr[front];
    }

    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列空，无数据~~");
        }

        for (int i = 0; i<arr.length;i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    /**
     * 获取头数据
     * @return
     */
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，无数据");
        }
        return arr[front + 1];
    }
}
