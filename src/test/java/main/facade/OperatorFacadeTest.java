package main.facade;

import main.logic.*;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by oglandx on 6/16/16.
 */
public class OperatorFacadeTest {
    OperatorFacade facade;
    PassengerFacade passengerFacade;
    DriverFacade driverFacade;

    private String email = "operator@somemail.ru";
    private String password = "12345";

    private Passenger passenger;
    private Operator operator;
    private Driver driver;

    @org.junit.Before
    public void setUp() throws Exception {
        facade = OperatorFacade.getInstance();
        passengerFacade = PassengerFacade.getInstance();
        driverFacade = DriverFacade.getInstance();

        assert facade != null;

        Operator currentOperator = new Operator(new RegisterData(
                "LastName",
                "FirstName",
                "MiddleName",
                new Date(0),
                email,
                password
        ));

        passenger = passengerFacade.auth(new AuthData("somemailmail@somemail.ru", "12345"));
        assert passenger != null;

        operator = facade.registerNew(currentOperator);
        assert (operator != null) || ((operator = facade.auth(currentOperator)) != null);

        driver = driverFacade.auth(new AuthData("somedriver@somemail.ru", "12345"));
        assert driver != null;

        driver.setStatus(DriverStatus.READY);
        driver.setStatus(DriverStatus.BUSY);
        assert driver.setStatus(DriverStatus.FREE);
        assert driverFacade.refreshDriver(driver) != null;
    }

    @org.junit.After
    public void tearDown() throws Exception {
        facade.closeConnection();
    }

    @Test
    public void auth() throws Exception {
        assert facade.auth(operator) != null;
    }

    @Test
    public void getOrderList() throws Exception {
        assert !facade.getOrderList().isEmpty();
    }

    @Test
    public void getFreeDrivers() throws Exception {
        assert !facade.getFreeDrivers().isEmpty();
    }

    @Test
    public void findBestDriver() throws Exception {
        List<Driver> drivers = facade.getFreeDrivers();
        assert !drivers.isEmpty();
        List<Order> orders = facade.getOrderList();
        assert !orders.isEmpty();
        assert facade.findBestDriver(operator, orders.get(0), drivers, new ChooseDriverStrategyBestKarma()) != null;
        assert facade.findBestDriver(operator, orders.get(0), drivers, new ChooseDriverStrategyWorstKarma()) != null;
    }

    @Test
    public void attachOrder() throws Exception {
        List<Driver> drivers = facade.getFreeDrivers();
        assert !drivers.isEmpty();
        List<Order> orders = facade.getOrderList();
        assert !orders.isEmpty();
        assert facade.attachOrder(operator, orders.get(0), drivers.get(0));
    }

    @Test
    public void killOrder() throws Exception {
        List<Order> orders = facade.getOrderList();
        assert !orders.isEmpty();
        assert facade.killOrder(operator, orders.get(0));
    }

}