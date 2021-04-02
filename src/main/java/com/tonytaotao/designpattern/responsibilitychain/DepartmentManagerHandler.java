package com.tonytaotao.designpattern.responsibilitychain;

public class DepartmentManagerHandler extends AbstractHandler {
    @Override
    public void handle(Integer days) {
        if (days <= 5) {
            System.out.println("部门经理审批通过");
        } else {
            if (getNext() != null) {
                getNext().handle(days);
            } else {
                System.out.println("结束");
            }
        }
    }
}
