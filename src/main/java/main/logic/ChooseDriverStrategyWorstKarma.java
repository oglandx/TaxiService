package main.logic;

import java.util.List;

/**
 * Created by oglandx on 6/14/16.
 */
public class ChooseDriverStrategyWorstKarma implements ChooseDriverStrategy {

    @Override
    public Driver solve(List<Driver> drivers) {
        Driver current = null;
        for(final Driver driver: drivers){
            if(current == null || driver.getKarma() < current.getKarma()){
                current = driver;
            }
        }

        return current;
    }
}
