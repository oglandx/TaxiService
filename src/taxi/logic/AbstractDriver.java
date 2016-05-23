package taxi.logic;

/**
 * Created by oglandx on 5/15/16.
 */
public interface AbstractDriver {
    void selectOrder(Order order);
    void declineOrder(Order order);
    boolean setStatus(final DriverStatus status);
    DriverStatus getStatus();

    boolean startWaiting();
    long endWaiting();
    void leaveWaiting();
    Payment getPayment(long distance);
    Rate getCurrentRate();
}
