package main.logic;

/**
 * Created by oglandx on 5/15/16.
 */
public interface AbstractDriver {
    boolean selectOrder(Order order);
    boolean declineOrder(Order order);
    Order getSelectedOrder();
    boolean setStatus(final DriverStatus status);
    DriverStatus getStatus();

    boolean startWaiting();
    int endWaiting();
    boolean leaveWaiting();
    Payment getPayment(int distance);
    Rate getCurrentRate();
    void setCurrentRate(Rate rate);
    boolean closeOrder();
}
