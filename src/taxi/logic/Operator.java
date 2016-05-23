package taxi.logic;

import java.util.*;

/**
 * Created by oglandx on 5/8/16.
 */
public class Operator extends User implements AbstractOperator {

    private ArrayList<Order> attached = new ArrayList<>();

    public Operator(){

    }

    @Override
    public boolean attachOrder(Order order) {
        if(!this.attached.contains(order) &&
                (order.getStatus() == OrderStatus.NEW || order.getStatus() == OrderStatus.DECLINED)){
            attached.add(order);
            order.setStatus(OrderStatus.PROCESSING);
            return true;
        }
        return false;
    }

    @Override
    public Order[] getAttachedOrders() {
        return (Order[]) attached.toArray();
    }

    @Override
    public Driver findBestDriver(Driver[] drivers, final Order order, final ChooseDriverStrategy strategy) {
        if(order.getDriver() != null && order.getStatus() == OrderStatus.DECLINED){
            ArrayList<Driver> _drivers = new ArrayList<>();
            for(final Driver driver: drivers){
                if(driver != order.getDriver()){
                    _drivers.add(driver);
                }
            }
            drivers = (Driver[]) _drivers.toArray();
        }
        return strategy.solve(drivers);
    }
}
