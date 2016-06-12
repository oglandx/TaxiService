package main.logic;

/**
 * Created by oglandx on 5/8/16.
 */
public interface AbstractOrder {
    /* Sets current status of order */
    boolean setStatus(final OrderStatus status);

    /* Retrieves current status of order */
    OrderStatus getStatus();

    /* Assigns passenger to the order */
    boolean assignPassenger(final Passenger passenger);

    /* Enables driver to bindDriver the order */
    boolean bindDriver(final Driver driver);

    /* Retrieves passenger */
    Passenger getPassenger();

    /* Retrieves current driver */
    Driver getDriver();
}
