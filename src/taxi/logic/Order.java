package taxi.logic;

/**
 * Created by oglandx on 5/8/16.
 */
public class Order extends Entity implements AbstractOrder {

    private OrderStatus status = OrderStatus.NEW;
    private Passenger passenger = null;
    private Driver driver = null;

    public Order(){

    }

    @Override
    public boolean setStatus(final OrderStatus status) {
        if(OrderStatus.isAvailable(this.status, status)){
            this.status = status;
            return true;
        }
        return false;
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
}
