package com.tonytaotao.designpattern.decorator;

/**
 * 装饰器模式
 */
public class DecoratorPattern {

    public static void main(String[] args) {
        Tea tea = new MilkTea();
        tea = new SugarTea(tea);
        tea = new IceTea(tea);

        System.out.println(tea.desc());
        System.out.println(tea.cost());
    }

}





