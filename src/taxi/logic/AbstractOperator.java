package taxi.logic;

/**
 * Created by oglandx on 5/22/16.
 */
public interface AbstractOperator {
    boolean attachOrder(Order order);
    Order[] getAttachedOrders();
    Driver findBestDriver(Driver[] drivers, Order order, ChooseDriverStrategy strategy);
}
