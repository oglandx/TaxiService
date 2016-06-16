package main.facade;

import main.logic.*;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by oglandx on 6/16/16.
 */
public class PassengerFacadeTest {

    PassengerFacade facade;
    OperatorFacade operatorFacade;
    DriverFacade driverFacade;
    private String email = "somemailmail@somemail.ru";
    private String password = "12345";
    private Passenger passenger;
    private Operator operator;
    private Driver driver;

    @org.junit.Before
    public void setUp() throws Exception {
        facade = PassengerFacade.getInstance();
        operatorFacade = OperatorFacade.getInstance();
        driverFacade = DriverFacade.getInstance();

        assert facade != null;

        Passenger currentPassenger = new Passenger(new RegisterData(
                "LastName",
                "FirstName",
                "MiddleName",
                new Date(0),
                email,
                password
        ));

        operator = operatorFacade.auth(new AuthData("kryukov@mail.ru", "12345"));

        passenger = facade.registerNew(currentPassenger);
        assert (passenger != null) || ((passenger = facade.auth(currentPassenger)) != null);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        facade.closeConnection();
    }

    @Test
    public void auth() throws Exception {
        assert facade.auth(passenger) != null;
    }

    @Test
    public void createOrder() throws Exception {
        assert facade.createOrder(passenger, new Address("", "", ""));
    }

    @Test
    public void getOrderList() throws Exception {
        assert facade.createOrder(passenger, new Address("", "", ""));
        assert !facade.getOrderList(passenger, null).isEmpty();
    }

    @Test
    public void killOrder() throws Exception {
        assert facade.createOrder(passenger, new Address("", "", ""));
        assert facade.killOrder(passenger, facade.getOrderList(passenger, OrderStatus.NEW).get(0));
    }

    @Test
    public void isOrderRated() throws Exception {
        assert facade.createOrder(passenger, new Address("", "", ""));
        assert !facade.isOrderRated(facade.getOrderList(passenger, null).get(0));
    }

    @Test
    public void rateOrder() throws Exception {
        assert facade.createOrder(passenger, new Address("", "", ""));
        List<Driver> drivers = operatorFacade.getFreeDrivers();
        List<Order> orders = facade.getOrderList(passenger, OrderStatus.NEW);
        Order order = null;
        if (drivers != null) {
            Driver driver = drivers.get(0);
            order = orders.get(0);
            operatorFacade.attachOrder(operator, order, driver);
            driverFacade.selectOrder(driver, order);
            driverFacade.startWaiting(driver);
            driverFacade.endWaiting(driver);
            List<Rate> rates = driverFacade.getAvailableRates();
            driverFacade.setCurrentRate(driver, rates.get(0));
            driverFacade.getPayment(driver, 100);
            driverFacade.closeOrder(driver);
        }
        assert facade.rateOrder(order, 10);
        assert order != null && order.getRating() == 10;
    }

}