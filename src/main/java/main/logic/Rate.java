package main.logic;

import java.math.BigDecimal;

/**
 * Created by oglandx on 5/22/16.
 */
public class Rate extends Entity {
    private BigDecimal costPerKm;
    private BigDecimal costPerMin;
    private int freeMinutes;

    public BigDecimal getCostPerKm() {
        return costPerKm;
    }

    public void setCostPerKm(BigDecimal costPerKm) {
        this.costPerKm = costPerKm;
    }

    public BigDecimal getCostPerMin() {
        return costPerMin;
    }

    public void setCostPerMin(BigDecimal costPerMin) {
        this.costPerMin = costPerMin;
    }

    public int getFreeMinutes() {
        return freeMinutes;
    }

    public void setFreeMinutes(int freeMinutes) {
        this.freeMinutes = freeMinutes;
    }

    public Rate(int id, final BigDecimal costPerKm, final BigDecimal costPerMin, int freeMinutes){
        setCostPerKm(costPerKm);
        setCostPerMin(costPerMin);
        setFreeMinutes(freeMinutes);
        setId(id);
    }

    public Rate(final BigDecimal costPerKm, final BigDecimal costPerMin, int freeMinutes){
        setCostPerKm(costPerKm);
        setCostPerMin(costPerMin);
        setFreeMinutes(freeMinutes);
    }
}
