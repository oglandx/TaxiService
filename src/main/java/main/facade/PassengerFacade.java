package main.facade;

import main.common.Query;
import main.database.AddressDataMapper;
import main.database.OrderDataMapper;
import main.database.PassengerDataMapper;
import main.logic.*;
import main.repository.AddressRepository;
import main.repository.OrderRepository;
import main.repository.PassengerRepository;
import main.repository.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by oglandx on 6/12/16.
 */
public class PassengerFacade implements UserFacade<Passenger> {
    private static PassengerFacade instance = null;

    private static PassengerRepository repository = null;
    private static OrderRepository orderRepository = null;
    private static AddressRepository addressRepository = null;

    private PassengerFacade() throws ApplicationError {
        if (repository == null) {
            PassengerDataMapper dataMapper = null;
            try {
                dataMapper = new PassengerDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            repository = new PassengerRepository(dataMapper);
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
        if(addressRepository == null){
            AddressDataMapper dataMapper = null;
            try {
                dataMapper = new AddressDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            addressRepository = new AddressRepository(dataMapper);
        }
    }

    public static PassengerFacade getInstance() throws ApplicationError {
        if (instance == null) {
            instance = new PassengerFacade();
        }
        return instance;
    }

    @Override
    public Passenger registerNew(RegisterData regData) throws ApplicationError {
        Query query = new Query("{'email': '" + regData.getEmail() + "'}");
        if(Util.checkQuery(repository, query)) {
            return null;
        }

        Passenger passenger;
        try {
            repository.create(new Passenger(regData));
            passenger = Util.extract(repository, query);
        }
        catch (DatabaseException e){
            e.printStackTrace();
            return null;
        }
        return passenger;
    }

    @Override
    public Passenger registerNew(Passenger passenger) throws ApplicationError {
        return registerNew(passenger.getRegData());
    }

    @Override
    public Passenger auth(AuthData authData) throws ApplicationError {
        String query = "{'email': '" + authData.getEmail() + "', 'pass': '" + authData.getPassHash() + "'}";
        return Util.extract(repository, new Query(query));
    }

    @Override
    public Passenger auth(Passenger passenger) throws ApplicationError {
        return auth(passenger.getRegData().getAuthData());
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

    public boolean createOrder(Passenger passenger, Address address) throws ApplicationError {
        String query = "{'city': '" + address.getCity() + "', 'street': '" + address.getStreet() +
                "', 'building': '" + address.getBuilding() + "'}";
        Address addressInDb = Util.extract(addressRepository, new Query(query));
        if(addressInDb == null){
            try {
                addressRepository.create(address);
            }
            catch (DatabaseException e) {
                throw new ApplicationError(e);
            }
            addressInDb = Util.extract(addressRepository, new Query(query));
        }
        Order order = new Order(addressInDb);
        if (!passenger.acceptOrder(order)) {
            return false;
        }
        try {
           orderRepository.create(order);
        }
        catch (DatabaseException e){
            throw new ApplicationError(e);
        }
        return true;
    }

    public List<Order> getOrderList(Passenger passenger, OrderStatus status) throws ApplicationError {
        String query;
        if (status == null) {
            query = "{'passenger_id': '" + passenger.getId() + "'}";
        }
        else {
            query = "{'passenger_id': '" + passenger.getId() + "', 'status': '" + status.getId() + "'}";
        }
        List<Order> orders;
        try {
            orders = orderRepository.filter(new Query(query));
        }
        catch (DatabaseException e) {
            throw new ApplicationError(e);
        }
        return orders;
    }

    public boolean declineOrder(Passenger passenger, Order order) throws ApplicationError {
        if (!passenger.declineOrder(order)) {
            return false;
        }
        try {
            orderRepository.update(order);
        }
        catch (DatabaseException e){
            throw new ApplicationError(e);
        }
        return true;
    }
}
