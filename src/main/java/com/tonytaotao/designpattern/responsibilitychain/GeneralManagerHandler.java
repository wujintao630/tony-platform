package com.tonytaotao.designpattern.responsibilitychain;

public class GeneralManagerHandler extends AbstractHandler {

    @Override
    public void handle(Integer days) {
        if (days > 5) {
            System.out.println("总经理审批通过");
        }

        System.out.println("结束");
    }

}
