package main.logic;

import main.database.OrderDataMapper;

import java.sql.SQLException;

/**
 * Created by oglandx on 5/8/16.
 */
public class Passenger extends UserWithKarma {

    public Passenger(int id, RegisterData regData, int karma){
        setId(id);
        register(regData);
        setKarma(karma);
    }

    public Passenger(RegisterData regData){
        register(regData);
    }

    public Order createOrder() throws SQLException {
        Order order = new Order();
        order.assignPassenger(this);
        OrderDataMapper orderDataMapper = new OrderDataMapper();
        orderDataMapper.insert(order);
        return order;
    }
}
