package com.tonytaotao.designpattern;

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


 interface Sport {

    void doSport();

}

class Running implements Sport {
    @Override
    public void doSport() {
        System.out.println("跑步");
    }
}

class Playing implements Sport {
    @Override
    public void doSport() {
        System.out.println("打球");
    }
}

class SportContext implements Sport {

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
