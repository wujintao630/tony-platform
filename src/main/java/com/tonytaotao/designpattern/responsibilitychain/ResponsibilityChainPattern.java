package com.tonytaotao.designpattern.responsibilitychain;

/**
 * 责任链模式
 * @author tonytaotao
 */
public class ResponsibilityChainPattern {

    public static void main(String[] args) {
        AbstractHandler  directLeader = new DirectLeaderHandler();
        AbstractHandler departmentManager = new DepartmentManagerHandler();
        AbstractHandler generalManager = new GeneralManagerHandler();

        directLeader.setNext(departmentManager);
        departmentManager.setNext(generalManager);

        directLeader.handle(5);
    }

}
