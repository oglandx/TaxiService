package main.logic;

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

    public boolean acceptOrder(Order order) {
        return order.getStatus().eq(OrderStatus.NEW) && order.assignPassenger(this);
    }

    public boolean declineOrder(Order order) {
        return order.getPassenger().getId() == getId() && order.setStatus(OrderStatus.DECLINED);
    }
}
