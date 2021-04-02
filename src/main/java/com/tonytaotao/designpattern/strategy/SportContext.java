package com.tonytaotao.designpattern.strategy;

public class SportContext implements Sport {

    private Sport sport;

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @Override
    public void doSport() {
        sport.doSport();
    }

}
