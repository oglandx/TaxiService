package main.facade;

import main.common.Query;
import main.database.DriverDataMapper;
import main.database.OrderDataMapper;
import main.database.PaymentDataMapper;
import main.database.RateDataMapper;
import main.logic.*;
import main.repository.DriverRepository;
import main.repository.OrderRepository;
import main.repository.PaymentRepository;
import main.repository.RateRepository;
import main.repository.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by oglandx on 6/12/16.
 */
public class DriverFacade implements UserFacade<Driver> {
    private static DriverFacade instance = null;

    private static DriverRepository repository = null;
    private static OrderRepository orderRepository = null;
    private static RateRepository rateRepository = null;
    private static PaymentRepository paymentRepository = null;

    private DriverFacade() throws ApplicationError {
        if (repository == null) {
            DriverDataMapper dataMapper = null;
            try {
                dataMapper = new DriverDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            repository = new DriverRepository(dataMapper);
        }
        if(orderRepository == null){
            OrderDataMapper dataMapper = null;
            try {
                dataMapper = new OrderDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            orderRepository = new OrderRepository(dataMapper);
        }
        if(rateRepository == null){
            RateDataMapper dataMapper = null;
            try {
                dataMapper = new RateDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            rateRepository = new RateRepository(dataMapper);
        }
        if(paymentRepository == null){
            PaymentDataMapper dataMapper = null;
            try {
                dataMapper = new PaymentDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            paymentRepository = new PaymentRepository(dataMapper);
        }
    }

    private DriverFacade(DriverRepository driverRepository, OrderRepository orderRepository,
                         RateRepository rateRepository, PaymentRepository paymentRepository) {
        repository = driverRepository;
        DriverFacade.orderRepository = orderRepository;
        DriverFacade.rateRepository = rateRepository;
        DriverFacade.paymentRepository = paymentRepository;
    }

    public static DriverFacade getInstance() throws ApplicationError {
        if (instance == null) {
            instance = new DriverFacade();
        }
        return instance;
    }

    public static DriverFacade initInstance(DriverRepository driverRepository, OrderRepository orderRepository,
                                            RateRepository rateRepository, PaymentRepository paymentRepository) {
        if (instance == null) {
            instance = new DriverFacade(driverRepository, orderRepository, rateRepository, paymentRepository);
        }
        return instance;
    }

    @Override
    public Driver registerNew(RegisterData regData) throws ApplicationError {
        Query query = new Query("{'email': '" + regData.getEmail() + "'}");
        if(Util.checkQuery(repository, query)) {
            return null;
        }

        Driver driver;
        try {
            repository.create(new Driver(regData));
            driver = Util.extract(repository, query);
        }
        catch (DatabaseException e){
            e.printStackTrace();
            return null;
        }
        return driver;
    }

    @Override
    public Driver registerNew(Driver driver) throws ApplicationError {
        return registerNew(driver.getRegData());
    }

    @Override
    public Driver auth(AuthData authData) throws ApplicationError {
        String query = "{'email': '" + authData.getEmail() + "', 'pass': '" + authData.getPassHash() + "'}";
        return Util.extract(repository, new Query(query));
    }

    @Override
    public Driver auth(Driver driver) throws ApplicationError {
        return auth(driver.getRegData().getAuthData());
    }

    @Override
    public boolean closeConnection() {
        try {
            repository.closeDataMapperConnection();
        }
        catch (DatabaseException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Order> getOrderList(Driver driver) throws ApplicationError {
        String query = "{'status': '" + OrderStatus.PROCESSING + "'}";
        List<Order> orders;
        try {
            orders = orderRepository.filter(new Query(query));
        }
        catch (DatabaseException e) {
            throw new ApplicationError(e);
        }
        return orders;
    }

    public boolean selectOrder(Driver driver, Order order) throws ApplicationError {
        if (!driver.selectOrder(order)) {
            return false;
        }
        try {
            orderRepository.update(order);
            repository.update(driver);
        }
        catch (DatabaseException e){
            throw new ApplicationError(e);
        }
        return true;
    }

    public boolean declineOrder(Driver driver, Order order) throws ApplicationError {
        if (!driver.declineOrder(order)) {
            return false;
        }
        try {
            orderRepository.update(order);
            repository.update(driver);
        }
        catch (DatabaseException e) {
            throw new ApplicationError(e);
        }
        return true;
    }

    public boolean setStatus(Driver driver, DriverStatus status) throws ApplicationError {
        if (!driver.setStatus(status)) {
            return false;
        }
        try {
            repository.update(driver);
        }
        catch (DatabaseException e) {
            throw new ApplicationError(e);
        }
        return true;
    }

    public DriverStatus getStatus(Driver driver) {
        return driver.getStatus();
    }

    public boolean startWaiting(Driver driver) {
        return driver.startWaiting();
    }

    public int endWaiting(Driver driver) {
        return driver.endWaiting();
    }

    public boolean leaveWaiting(Driver driver) throws ApplicationError {
        if (driver.leaveWaiting()) {
            try {
                orderRepository.update(driver.getSelectedOrder());
            } catch (DatabaseException e) {
                throw new ApplicationError(e);
            }
            return true;
        }
        return false;
    }

    public Payment getPayment(Driver driver, int distance) throws ApplicationError {
        Payment payment = driver.getPayment(distance);
        if (payment != null) {
            try {
                paymentRepository.create(payment);
                orderRepository.update(driver.getSelectedOrder());
            }
            catch (DatabaseException e){
                throw new ApplicationError(e);
            }
        }
        return payment;
    }

    public List<Rate> getAvailableRates() throws ApplicationError {
        List<Rate> rates;
        try {
            rates = rateRepository.all();
        }
        catch (DatabaseException e) {
            throw new ApplicationError(e);
        }
        return rates;
    }

    public void setCurrentRate(Driver driver, Rate rate) {
        driver.setCurrentRate(rate);
    }

    public Rate getCurrentRate(Driver driver) {
        return driver.getCurrentRate();
    }
}
