package com.tonytaotao.datastructure.queue;

import java.util.Scanner;

/**
 * 使用数组模拟队列
 */
public class ArrayCircleQueueDemo {

    public static void main(String[] args) {
        // 有效数据为3个，因为留有一个空位给rear
        ArrayCircleQuque arrayCircleQuque = new ArrayCircleQuque(4);
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
                    arrayCircleQuque.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入数据");
                    int value = scanner.nextInt();
                    arrayCircleQuque.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayCircleQuque.getQueue();
                        System.out.printf("取出数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = arrayCircleQuque.headQueue();
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

class ArrayCircleQuque {
    /**
     * 数组最大容量
     */
    private int maxSize;

    /**
     * 队列头:指向队列的第一个元素，arr[front]， front初始值为0
     */
    private int front;

    /**
     * 队列尾:指向队列的最后一个元素的后一个位置， rear初始值为0
     */
    private int rear;

    /**
     * 存放数据，模拟队列
     */
    private int[] arr;

    public ArrayCircleQuque(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
    }

    public  boolean isFull() {
        return (rear + 1) % maxSize ==  front;
    }

    public boolean isEmpty() {
        return rear == front;
    }

    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("队列满，无法加入");
            return;
        }
        // 直接添加数据
        arr[rear] = n;
        // 将real后移，必须考虑数组越界，因为要形成环形，所以取模
        rear = (rear + 1) % maxSize;
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，不能取数");
        }
        // 1、将front对应的数据保存临时变量
        // 2、将front后移
        // 3、返回临时变量
        int value = arr[front];
        front = (front + 1)% maxSize;
        return value;
    }

    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列空，无数据~~");
        }

        // 从front开始遍历，遍历多少个元素
        for (int i = front; i < (front + getValidSize());i++) {
            System.out.printf("arr[%d]=%d\n", i%maxSize, arr[i%maxSize]);
        }
    }

    /**
     * 获取环形队列中的有效元素个数
     * @return
     */
    public int getValidSize() {
        return (rear + maxSize - front)%maxSize;
    }

    /**
     * 获取头数据
     * @return
     */
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，无数据");
        }
        return arr[front];
    }
}
