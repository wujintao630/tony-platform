package com.tonytaotao.datastructure.stack;

public class Calculator {

    public static void main(String[] args) {

        String express = "70+6*2-40";

        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);

        int index = 0; // 用于扫描表达式
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' '; //每次扫描得到的char

        String keepNum = ""; // 拼接多位数

        while(true) {

            ch = express.substring(index, index+1).charAt(0);

            if (operStack.isOper(ch)) {
                if (!operStack.isEmpty()) {
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);
                        // 结果入栈
                        numStack.push(res);
                        // 运算符入栈
                        operStack.push(ch);
                    } else {
                        operStack.push(ch);
                    }
                } else {
                    operStack.push(ch);
                }
            } else {
                //numStack.push(ch - 48); // ch 表示字符 ，需要减去48才是数字
                keepNum += ch;

                if (index == express.length()-1) { // 防止index+2 越界
                    numStack.push(Integer.parseInt(keepNum));
                } else {
                    // 后一位是运算符
                    if (operStack.isOper(express.substring(index+1, index+2).charAt(0))) {
                        numStack.push(Integer.parseInt(keepNum));
                        keepNum = "";
                    }
                }
            }

            index++;
            if (index >= express.length()) {
                break;
            }

        }

        while (true) {
            if (operStack.isEmpty()) {
                break;
            }

            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            // 结果入栈
            numStack.push(res);
        }

        System.out.printf("表达式%s=%d", express, numStack.pop());

    }


}

class ArrayStack2 {

    private int maxSize; // 栈的大小

    private int[] stack; // 数组模拟栈

    private int top = -1; // 栈顶

    public ArrayStack2(int maxSize) {
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

    public int peek() {
        return stack[top];
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

    public int priority(int oper) {
        if (oper =='*' || oper == '/') {
            return 1;
        } else if(oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;
        }
    }

    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    public int cal (int num1, int num2, int oper) {
        int res = 0;
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2/num1;
                break;
            default:
                break;
        }

        return res;
    }
}

