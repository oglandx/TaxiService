package taxi.logic;

import java.math.BigDecimal;

/**
 * Created by oglandx on 5/22/16.
 */
public class Rate extends Entity {
    public final BigDecimal perKm;
    public final BigDecimal perMinute;
    public final long freeMinutes;

    public Rate(final BigDecimal perKm, final BigDecimal perMinute, long freeMinutes){
        this.perKm = perKm;
        this.perMinute = perMinute;
        this.freeMinutes = freeMinutes;
    }
}
