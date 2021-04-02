package com.tonytaotao.designpattern.decorator;

public class IceTea extends DecoratorTea {

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
