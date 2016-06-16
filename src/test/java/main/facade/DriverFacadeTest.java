package main.facade;

import main.logic.*;
import org.junit.Test;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by oglandx on 6/16/16.
 */
public class DriverFacadeTest {

    PassengerFacade passengerFacade;
    OperatorFacade operatorFacade;
    DriverFacade facade;
    private String email = "somedriver1@somemail.ru";
    private String password = "12345";
    private Passenger passenger;
    private Operator operator;
    private Driver driver;
    List<Driver> freeDrivers;
    List<Order> newOrders;

    @org.junit.Before
    public void setUp() throws Exception {
        facade = DriverFacade.getInstance();
        operatorFacade = OperatorFacade.getInstance();
        passengerFacade = PassengerFacade.getInstance();

        assert facade != null;

        Driver currentDriver = new Driver(new RegisterData(
                "LastName",
                "FirstName",
                "MiddleName",
                new Date(0),
                email,
                password
        ));

        operator = operatorFacade.auth(new AuthData("kryukov@mail.ru", "12345"));

        driver = facade.registerNew(currentDriver);
        assert (driver != null) || ((driver = facade.auth(currentDriver)) != null);

        newOrders = operatorFacade.getOrderList();
        assert !newOrders.isEmpty();

        driver.setStatus(DriverStatus.BUSY);
        driver.setStatus(DriverStatus.FREE);
        facade.refreshDriver(driver);
        freeDrivers = operatorFacade.getFreeDrivers().stream()
                .filter(e -> e.getId() == driver.getId())
                .collect(Collectors.toList());

        assert !freeDrivers.isEmpty();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        List<Order> orders;
        if (!(orders = facade.getOrderList(driver)).isEmpty()) {
            facade.declineOrder(driver, orders.get(0));
        }
        driver = facade.refreshDriver(driver);
        if (!orders.isEmpty() && orders.get(0).getStatus().eq(OrderStatus.EXECUTED)) {
            assert facade.declineOrder(driver, driver.getSelectedOrder());
        }
        facade.closeConnection();
    }

    @Test
    public void auth() throws Exception {
        assert facade.auth(driver) != null;
    }

    @Test
    public void getOrderList() throws Exception {
        assert operatorFacade.attachOrder(operator, newOrders.get(0), freeDrivers.get(0));
        driver = facade.refreshDriver(driver);

        assert facade.getOrderList(driver) != null;
    }

    @Test
    public void selectOrder() throws Exception {
        assert operatorFacade.attachOrder(operator, newOrders.get(0), freeDrivers.get(0));
        driver = facade.refreshDriver(driver);

        List<Order> orders = facade.getOrderList(driver);
        assert facade.selectOrder(driver, orders.get(0));
        assert facade.declineOrder(driver, orders.get(0));
    }

    @Test
    public void declineOrder() throws Exception {
        assert operatorFacade.attachOrder(operator, newOrders.get(0), freeDrivers.get(0));
        driver = facade.refreshDriver(driver);

        List<Order> orders = facade.getOrderList(driver);
        assert facade.declineOrder(driver, orders.get(0));
    }

    @Test
    public void setStatus() throws Exception {
        DriverStatus prevStatus = driver.getStatus();
        if (prevStatus == DriverStatus.READY) {
            assert facade.setStatus(driver, DriverStatus.BUSY);
            assert !facade.setStatus(driver, DriverStatus.READY);
            assert facade.setStatus(driver, DriverStatus.FREE);
            assert !facade.setStatus(driver, DriverStatus.BUSY);
            assert facade.setStatus(driver, DriverStatus.READY);
        }
    }

    @Test
    public void startWaiting() throws Exception {
        assert operatorFacade.attachOrder(operator, newOrders.get(0), freeDrivers.get(0));
        driver = facade.refreshDriver(driver);

        assert !facade.startWaiting(driver);
        Order order = facade.getOrderList(driver).get(0);
        assert facade.selectOrder(driver, order);
        assert facade.startWaiting(driver);
        assert facade.leaveWaiting(driver);
    }

    @Test
    public void endWaiting() throws Exception {
        assert operatorFacade.attachOrder(operator, newOrders.get(0), freeDrivers.get(0));
        driver = facade.refreshDriver(driver);

        Order order = facade.getOrderList(driver).get(0);
        assert facade.selectOrder(driver, order);
        assert !(facade.endWaiting(driver) > 0);
        assert facade.startWaiting(driver);
        assert facade.endWaiting(driver) >= 0;
        assert facade.declineOrder(driver, order);
    }

    @Test
    public void leaveWaiting() throws Exception {
        assert operatorFacade.attachOrder(operator, newOrders.get(0), freeDrivers.get(0));
        driver = facade.refreshDriver(driver);

        Order order = facade.getOrderList(driver).get(0);
        assert facade.selectOrder(driver, order);
        assert !facade.leaveWaiting(driver);
        assert facade.startWaiting(driver);
        assert facade.leaveWaiting(driver);
    }

    @Test
    public void getPayment() throws Exception {
        assert operatorFacade.attachOrder(operator, newOrders.get(0), freeDrivers.get(0));
        driver = facade.refreshDriver(driver);

        Order order = facade.getOrderList(driver).get(0);
        assert facade.selectOrder(driver, order);
        assert facade.startWaiting(driver);
        assert facade.endWaiting(driver) >= 0;

        List<Rate> rates = facade.getAvailableRates();
        assert !rates.isEmpty();
        facade.setCurrentRate(driver, rates.get(0));
        assert facade.getPayment(driver, 10) != null;
        assert facade.closeOrder(driver);
    }

    @Test
    public void getCurrentOrder() throws Exception {
        assert operatorFacade.attachOrder(operator, newOrders.get(0), freeDrivers.get(0));
        driver = facade.refreshDriver(driver);

        Order order = facade.getOrderList(driver).get(0);
        assert facade.selectOrder(driver, order);
        assert driver.getSelectedOrder().getId() == order.getId();
        assert facade.declineOrder(driver, order);
    }

    @Test
    public void refreshDriver() throws Exception {
        assert driver.getId() == facade.refreshDriver(driver).getId();
    }
}