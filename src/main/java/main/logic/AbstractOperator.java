package main.logic;

import java.util.List;

/**
 * Created by oglandx on 5/22/16.
 */
public interface AbstractOperator {
    boolean attachOrder(Order order);
    List<Order> getAttachedOrders();
    Driver findBestDriver(List<Driver> drivers, Order order, ChooseDriverStrategy strategy);
}
