package com.tonytaotao.designpattern.strategy;

/**
 * 策略模式
 */
public class StrategyPattern {

    public static void main(String[] args) {
        SportContext context = new SportContext();

        Running running = new Running();

        context.setSport(running);

        context.doSport();
    }

}
