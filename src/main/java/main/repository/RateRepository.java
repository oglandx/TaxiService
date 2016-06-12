package main.repository;

import main.logic.Rate;

/**
 * Created by oglandx on 6/6/16.
 */
public class RateRepository extends Repository<Rate> {

    @Override
    public void update(Rate item) {
        list.stream()
                .filter(entry -> entry.getId() == item.getId())
                .forEach(entry -> {
                    entry.setCostPerKm(item.getCostPerKm());
                    entry.setCostPerMin(item.getCostPerMin());
                    entry.setFreeMinutes(item.getFreeMinutes());
                });
    }
}