package com.tonytaotao.datastructure.stack;

import java.util.Scanner;

public class ArrayStackDemo {

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(4);

        String key = "";

        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        while(loop) {
            System.out.println("s(show),显示栈");
            System.out.println("e(exit),退出程序");
            System.out.println("pu(push),添加数据");
            System.out.println("po(pop),取出数据");

            key = scanner.next();
            switch (key) {
                case "s":
                    arrayStack.list();
                    break;
                case "pu":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    arrayStack.push(value);
                    break;
                case "po":
                    try {
                        int res = arrayStack.pop();
                        System.out.printf("取出数据为%d\n", res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "e":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }

        }

        System.out.println("程序退出");
    }


}

class ArrayStack {

    private int maxSize; // 栈的大小

    private int[] stack; // 数组模拟栈

    private int top = -1; // 栈顶

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(int value) {
        if(isFull()) {
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top] = value;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空");
        }

        int value = stack[top];
        top--;
        return value;
    }

    public void list() {
        if(isEmpty()) {
            System.out.println("栈空");
            return;
        }
        for (int i = top ; i>=0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }
}
