package com.tonytaotao.designpattern.strategy;

public class Playing implements Sport {

    @Override
    public void doSport() {
        System.out.println("打球");
    }

}
