package taxi.logic;

import java.math.BigDecimal;

/**
 * Created by oglandx on 5/22/16.
 */
public class Payment extends Entity {
    private long distance = 0;
    private long waitedMinutes = 0;
    private Rate rate;

    public Payment(final Rate rate){
        this.rate = rate;
    }

    public BigDecimal getDriveCost(){
        return new BigDecimal(this.distance).multiply(rate.perKm);
    }

    public BigDecimal getWaitCost(){
        return new BigDecimal(this.waitedMinutes).multiply(rate.perMinute);
    }

    public BigDecimal getFullCost(){
        return this.getDriveCost().add(this.getWaitCost());
    }

    public void setWaitedMinutes(long waitedMinutes) {
        this.waitedMinutes = waitedMinutes;
    }

    public long getWaitedMinutes(){
        return this.waitedMinutes;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getDistance(){
        return this.distance;
    }

    public Rate getRate(){
        return this.rate;
    }
}
