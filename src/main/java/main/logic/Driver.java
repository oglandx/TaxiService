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
    private Rate currentRate = null;

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
    public boolean selectOrder(Order order) {
        if(setStatus(DriverStatus.BUSY) &&
                order.bindDriver(this) &&
                order.setStatus(OrderStatus.ACCEPTED)) {
            selectedOrder = order;
            return true;
        }
        else{
            declineOrder(order);
            return false;
        }
    }

    public boolean selectOrder(Order order, boolean force) {
        if (force) {
            selectedOrder = order;
            return true;
        }
        return selectOrder(order);
    }

    @Override
    public boolean declineOrder(Order order) {
        selectedOrder = null;
        return order.getDriver().getId() == getId() &&
                setStatus(DriverStatus.FREE) &&
                order.bindDriver(this) &&
                order.setStatus(OrderStatus.DECLINED);
    }

    public Order getSelectedOrder(){
        return selectedOrder;
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
        if (selectedOrder == null || this.waitingStart != null){
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        this.waitingStart = calendar.getTime();
        return true;
    }

    @Override
    public int endWaiting() {
        if (waitingStart == null) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        waitedTime = (int)(currentTime.getTime() - waitingStart.getTime());
        waitingStart = null;
        return waitedTime;
    }

    @Override
    public boolean leaveWaiting() {
        waitedTime = 0;
        boolean started = waitingStart != null;
        waitingStart = null;
        return selectedOrder != null && started &&
                setStatus(DriverStatus.FREE) && selectedOrder.setStatus(OrderStatus.DECLINED);
    }

    @Override
    public Payment getPayment(int distance) {
        if (selectedOrder == null) {
            return null;
        }
        Payment payment = new Payment(this.getCurrentRate());
        payment.setDistance(distance);
        payment.setWaitMin(waitedTime/60);
        if (selectedOrder != null) {
            selectedOrder.setPayment(payment);
        }
        return payment;
    }

    @Override
    public boolean closeOrder() {
        return selectedOrder != null && selectedOrder.setStatus(OrderStatus.EXECUTED) && setStatus(DriverStatus.FREE);
    }

    @Override
    public Rate getCurrentRate() {
        return currentRate;
    }

    @Override
    public void setCurrentRate(Rate rate) {
        this.currentRate = rate;
    }

    @Override
    public String toString() {
        return getLastname() + " " + getFirstname() + " " + getMiddlename();
    }
}
