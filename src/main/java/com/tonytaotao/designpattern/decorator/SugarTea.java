package com.tonytaotao.designpattern.decorator;

public class SugarTea extends DecoratorTea {

    private Tea tea;

    public SugarTea(Tea tea) {
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
