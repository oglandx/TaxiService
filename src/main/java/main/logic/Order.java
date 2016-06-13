package main.logic;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by oglandx on 5/8/16.
 */
public class Order extends Entity implements AbstractOrder {

    private OrderStatus status = OrderStatus.NEW;
    private Passenger passenger = null;
    private Driver driver = null;
    private Timestamp creationTime;
    private Address address = null;
    private Payment payment = null;

    public Order(Address address){
        creationTime = new Timestamp(new Date().getTime());
        setAddress(address);
    }

    public Order(int id, Timestamp creationTime, String status, Address address,
                 Payment payment, Driver driver, Passenger passenger){
        setId(id);
        this.creationTime = creationTime;
        setStatus(OrderStatus.valueOf(status), true);
        setAddress(address);
        setPayment(payment);
        assignPassenger(passenger);
        bindDriver(driver);
    }

    @Override
    public boolean setStatus(final OrderStatus status) {
        if(OrderStatus.isAvailable(this.status, status)){
            this.status = status;
            return true;
        }
        return false;
    }

    private boolean setStatus(final OrderStatus status, boolean force) {
        if(force) {
            this.status = status;
        }
        return setStatus(status);
    }

    @Override
    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public boolean assignPassenger(final Passenger passenger) {
        if(this.passenger == null && passenger != null){
            this.passenger = passenger;
            return true;
        }
        return false;
    }

    @Override
    public boolean bindDriver(final Driver driver) {
        if (this.driver == null && driver != null) {
            this.driver = driver;
            return true;
        }
        return false;
    }

    @Override
    public Passenger getPassenger() {
        return passenger;
    }

    @Override
    public Driver getDriver() {
        return driver;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
