package com.tonytaotao.designpattern;

/**
 * 装饰器模式
 */
public class DecoratorPattern {

    public static void main(String[] args) {
        Tea tea = new MilkTea();
        tea = new sugarTea(tea);
        tea = new IceTea(tea);

        System.out.println(tea.desc());
        System.out.println(tea.cost());
    }

}

interface Tea {
    String desc();

    Integer cost();
}

class MilkTea implements Tea {

    @Override
    public String desc() {
        return "奶茶";
    }

    @Override
    public Integer cost() {
        return 10;
    }
}

abstract class DecoratorTea implements Tea {


}

class sugarTea extends DecoratorTea {

    private Tea tea;

    public sugarTea(Tea tea) {
        this.tea = tea;
    }

    @Override
    public String desc() {
        return tea.desc() + "+糖";
    }

    @Override
    public Integer cost() {
        return tea.cost() + 1;
    }
}

class IceTea extends DecoratorTea {

    private Tea tea;

    public IceTea(Tea tea) {
        this.tea = tea;
    }

    @Override
    public String desc() {
        return tea.desc() + "+冰";
    }

    @Override
    public Integer cost() {
        return tea.cost() + 3;
    }
}




