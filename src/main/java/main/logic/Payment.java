package main.logic;

import java.math.BigDecimal;

/**
 * Created by oglandx on 5/22/16.
 */
public class Payment extends Entity {
    private int distance = 0;
    private int waitMin = 0;
    private Rate rate;

    public Payment(final Rate rate){
        this.rate = rate;
    }

    public Payment(int id, int distance, int waitMin, Rate rate){
        setId(id);
        setDistance(distance);
        setWaitMin(waitMin);
        this.rate = rate;
    }

    public BigDecimal getDriveCost(){
        return new BigDecimal(this.distance).multiply(rate.getCostPerKm());
    }

    public BigDecimal getWaitCost(){
        return new BigDecimal(this.waitMin).multiply(rate.getCostPerMin());
    }

    public BigDecimal getFullCost(){
        return this.getDriveCost().add(this.getWaitCost());
    }

    public void setWaitMin(int waitMin) {
        this.waitMin = waitMin;
    }

    private int prepareWaitMin() {
        int mins = waitMin - getRate().getFreeMinutes();
        return this.waitMin = mins > 0 ? mins : 0;
    }

    public int getWaitMin(){
        return this.waitMin;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance(){
        return this.distance;
    }

    public Rate getRate(){
        return this.rate;
    }
}
