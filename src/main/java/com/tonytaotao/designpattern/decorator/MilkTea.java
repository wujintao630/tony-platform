package com.tonytaotao.designpattern.decorator;

public class MilkTea implements Tea {

    @Override
    public String desc() {
        return "奶茶";
    }

    @Override
    public Integer cost() {
        return 10;
    }

}
