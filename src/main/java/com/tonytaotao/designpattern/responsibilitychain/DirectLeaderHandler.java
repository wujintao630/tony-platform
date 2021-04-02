package com.tonytaotao.designpattern.responsibilitychain;

public class DirectLeaderHandler extends AbstractHandler{

    @Override
    public void handle(Integer days) {

        if (days <= 3) {
            System.out.println("直接上级审批通过");
        } else {
            if (getNext() != null) {
                getNext().handle(days);
            } else {
                System.out.println("结束");
            }
        }

    }
}
