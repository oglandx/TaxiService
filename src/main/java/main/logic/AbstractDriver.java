package main.logic;

/**
 * Created by oglandx on 5/15/16.
 */
public interface AbstractDriver {
    void selectOrder(Order order);
    void declineOrder(Order order);
    boolean setStatus(final DriverStatus status);
    DriverStatus getStatus();

    boolean startWaiting();
    int endWaiting();
    void leaveWaiting();
    Payment getPayment(int distance);
    Rate getCurrentRate();
}
