package main.repository;

import main.logic.Order;

/**
 * Created by oglandx on 5/23/16.
 */
public class OrderRepository extends Repository<Order> {

    @Override
    public void update(Order item) {
        list.stream()
                .filter(entry -> entry.getId() == item.getId())
                .forEach(entry -> {
                    entry.bindDriver(item.getDriver());
                    entry.assignPassenger(item.getPassenger());
                });
    }
}
