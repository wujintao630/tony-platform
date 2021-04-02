package com.tonytaotao.designpattern.responsibilitychain;

public abstract class AbstractHandler implements Handler {

    private Handler next;

    public Handler getNext() {
        return next;
    }

    public void setNext(Handler next) {
        this.next = next;
    }
}
