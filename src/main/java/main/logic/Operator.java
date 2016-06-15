package main.logic;

import com.sun.org.apache.xpath.internal.operations.Or;
import main.common.Query;
import main.database.DriverDataMapper;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by oglandx on 5/8/16.
 */
public class Operator extends User implements AbstractOperator {

    private ArrayList<Order> attached = new ArrayList<>();

    @Override
    public boolean attachOrder(Order order, Driver driver) {
        if(!this.attached.contains(order) &&
                (order.getStatus() == OrderStatus.NEW || order.getStatus() == OrderStatus.DECLINED)){
            attached.add(order);
            return order.setStatus(OrderStatus.PROCESSING) && order.setDriver(driver);
        }
        return false;
    }

    @Override
    public List<Order> getAttachedOrders() {
        return attached;
    }

    @Override
    public Driver findBestDriver(List<Driver> drivers, final Order order, final ChooseDriverStrategy strategy) {
        if(order.getDriver() != null && order.getStatus() == OrderStatus.DECLINED){
            ArrayList<Driver> _drivers = new ArrayList<>();
            for(final Driver driver: drivers){
                if(driver != order.getDriver()){
                    _drivers.add(driver);
                }
            }
            drivers = _drivers;
        }
        return strategy.solve(drivers);
    }

    public Operator(int id, RegisterData regData){
        setId(id);
        register(regData);
    }

    public Operator(RegisterData regData){
        register(regData);
    }

    public boolean killOrder(Order order) {
        return order.setStatus(OrderStatus.DEAD);
    }
}
