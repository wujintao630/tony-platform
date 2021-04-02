package com.tonytaotao.designpattern.strategy;

public class Running implements Sport {

    @Override
    public void doSport() {
        System.out.println("跑步");
    }

}
