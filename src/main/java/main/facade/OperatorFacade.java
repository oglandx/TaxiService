package main.facade;

import main.common.Query;
import main.database.DriverDataMapper;
import main.database.OperatorDataMapper;
import main.database.OrderDataMapper;
import main.database.RateDataMapper;
import main.logic.*;
import main.repository.DriverRepository;
import main.repository.OperatorRepository;
import main.repository.OrderRepository;
import main.repository.RateRepository;
import main.repository.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by oglandx on 6/12/16.
 */
public class OperatorFacade implements UserFacade<Operator> {
    private static OperatorFacade instance = null;

    private static OperatorRepository repository = null;
    private static OrderRepository orderRepository = null;
    private static DriverRepository driverRepository = null;
    private static RateRepository rateRepository = null;

    private OperatorFacade() throws ApplicationError {
        if (repository == null) {
            OperatorDataMapper dataMapper = null;
            try {
                dataMapper = new OperatorDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            repository = new OperatorRepository(dataMapper);
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
        if(driverRepository == null){
            DriverDataMapper dataMapper = null;
            try {
                dataMapper = new DriverDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            driverRepository = new DriverRepository(dataMapper);
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
    }

    private OperatorFacade(OperatorRepository operatorRepository, DriverRepository driverRepository,
                         OrderRepository orderRepository, RateRepository rateRepository) {
        repository = operatorRepository;
        OperatorFacade.driverRepository = driverRepository;
        OperatorFacade.orderRepository = orderRepository;
        OperatorFacade.rateRepository = rateRepository;
    }

    public static OperatorFacade getInstance() throws ApplicationError{
        if (instance == null) {
            instance = new OperatorFacade();
        }
        return instance;
    }

    public static OperatorFacade initInstance(OperatorRepository operatorRepository, DriverRepository driverRepository,
                                              OrderRepository orderRepository, RateRepository rateRepository) {
        if (instance == null) {
            instance = new OperatorFacade(operatorRepository, driverRepository, orderRepository, rateRepository);
        }
        return instance;
    }

    @Override
    public Operator registerNew(RegisterData regData) throws ApplicationError {
        Query query = new Query("{'email': '" + regData.getEmail() + "'}");
        if(Util.checkQuery(repository, query)) {
            return null;
        }

        Operator operator;
        try {
            repository.create(new Operator(regData));
            operator = Util.extract(repository, query);
        }
        catch (DatabaseException e){
            e.printStackTrace();
            return null;
        }
        return operator;
    }

    @Override
    public Operator registerNew(Operator operator) throws ApplicationError {
        return registerNew(operator.getRegData());
    }

    @Override
    public Operator auth(AuthData authData) throws ApplicationError {
        String query = "{'email': '" + authData.getEmail() + "', 'pass': '" + authData.getPassHash() + "'}";
        return Util.extract(repository, new Query(query));
    }

    @Override
    public Operator auth(Operator operator) throws ApplicationError {
        return auth(operator.getRegData().getAuthData());
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

    public List<Order> getOrderList() throws ApplicationError {
        String query = "{'status__in': '" + OrderStatus.NEW + "," + OrderStatus.DECLINED + "'}";
        List<Order> orders;
        try {
            orders = orderRepository.filter(new Query(query));
        }
        catch (DatabaseException e) {
            throw new ApplicationError(e);
        }
        return orders;
    }

    public List<Driver> getFreeDrivers() throws ApplicationError {
        Query query = new Query("{'status': '" + DriverStatus.FREE + "'}");
        List<Driver> drivers;
        try {
            drivers = driverRepository.filter(query);
        }
        catch (DatabaseException e){
            throw new ApplicationError(e);
        }
        return drivers;
    }

    public Driver findBestDriver(Operator operator, Order order, List<Driver> freeDrivers,
                                 ChooseDriverStrategy strategy) throws ApplicationError {
        Driver result = operator.findBestDriver(freeDrivers, order, strategy);
        try {
            driverRepository.update(result);
        }
        catch (DatabaseException e){
            throw new ApplicationError(e);
        }
        return result;
    }

    public boolean attachOrder(Operator operator, Order order, Driver driver) throws ApplicationError {
        if (operator.attachOrder(order, driver)) {
            try {
                orderRepository.update(order);
                driverRepository.update(driver);
            } catch (DatabaseException e) {
                throw new ApplicationError(e);
            }
            return true;
        }
        return false;
    }

    public boolean killOrder(Operator operator, Order order) throws ApplicationError {
        if (!operator.killOrder(order)) {
            return false;
        }
        try {
            orderRepository.update(order);
        }
        catch (DatabaseException e) {
            throw new ApplicationError(e);
        }
        return true;
    }

    public boolean canFindOptimalDriver(Order order) {
        return order != null && !order.getStatus().eq(OrderStatus.DECLINED);
    }

    public boolean canKillOrder(Order order) {
        return order != null && order.getStatus().eq(OrderStatus.DECLINED);
    }

    public boolean addRate(Rate rate) throws ApplicationError {
        if (rate == null) {
            return false;
        }
        try {
            rateRepository.create(rate);
        }
        catch (DatabaseException e) {
            throw new ApplicationError(e);
        }
        return true;
    }
}
