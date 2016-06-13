package main.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by oglandx on 5/8/16.
 */
public class Driver extends UserWithKarma implements AbstractDriver {
    private Order selectedOrder = null;
    private DriverStatus status = DriverStatus.FREE;
    private Date waitingStart = null;
    private int waitedTime = 0;
    private ArrayList<Order> availableOrders = new ArrayList<>();

    public Driver(int id, RegisterData regData, int karma, DriverStatus status){
        setId(id);
        register(regData);
        setKarma(karma);
        setStatus(status);
    }

    public Driver(RegisterData regData){
        register(regData);
    }

    @Override
    public void selectOrder(Order order) {
        if(this.setStatus(DriverStatus.BUSY)) {
            this.selectedOrder = order;
            order.bindDriver(this);
            order.setStatus(OrderStatus.ACCEPTED);
        }
        else{
            this.declineOrder(order);
        }
    }

    @Override
    public void declineOrder(Order order) {
        this.selectedOrder = null;
        order.bindDriver(this);
        order.setStatus(OrderStatus.DECLINED);
    }

    @Override
    public boolean setStatus(DriverStatus status) {
        if(DriverStatus.isAvailable(this.status, status)){
            this.status = status;
            return true;
        }
        return false;
    }

    @Override
    public DriverStatus getStatus() {
        return this.status;
    }

    @Override
    public boolean startWaiting() {
        if(this.waitingStart != null){
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        this.waitingStart = calendar.getTime();
        return true;
    }

    @Override
    public int endWaiting() {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        this.waitedTime = (int)(currentTime.getTime() - this.waitingStart.getTime());
        this.waitingStart = null;
        return this.waitedTime;
    }

    @Override
    public void leaveWaiting() {
        this.waitingStart = null;
        this.waitedTime = 0;
        this.selectedOrder.setStatus(OrderStatus.DECLINED);
    }

    @Override
    public Payment getPayment(int distance) {
        Payment payment = new Payment(this.getCurrentRate());
        payment.setDistance(distance);
        payment.setWaitMin(waitedTime/60);
        return payment;
    }

    @Override
    public Rate getCurrentRate() {
        return new Rate(new BigDecimal(0), new BigDecimal(0), 0);
    }

}
