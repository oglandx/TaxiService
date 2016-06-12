package main.repository;

import main.logic.Driver;

/**
 * Created by oglandx on 5/23/16.
 */
public class DriverRepository extends Repository<Driver> {

    @Override
    public void update(Driver item) {
        list.stream()
                .filter(entry -> entry.getId() == item.getId())
                .forEach(entry -> entry.register(item.getRegData()));
    }
}
