package main.logic;

import java.util.List;

/**
 * Created by oglandx on 5/16/16.
 */
public class ChooseDriverStrategyBestKarma implements ChooseDriverStrategy {

    @Override
    public Driver solve(List<Driver> drivers) {
        Driver current = null;
        for(final Driver driver: drivers){
            if(current == null || driver.getKarma() > current.getKarma()){
                current = driver;
            }
        }

        return current;
    }
}
