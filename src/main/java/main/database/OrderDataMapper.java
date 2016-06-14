package main.database;

import main.logic.*;
import main.common.Query;
import main.logic.Driver;

import java.sql.*;
import java.util.List;

/**
 * Created by oglandx on 5/30/16.
 */
public class OrderDataMapper extends SimpleTableDataMapper<Order>{

    public OrderDataMapper() throws SQLException {
        getConnection();
    }

    @Override
    Order current(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        Timestamp creationtime = resultSet.getTimestamp("creationtime");
        String status = resultSet.getString("status");
        int address_id = resultSet.getInt("address_id");
        int payment_id = resultSet.getObject("payment_id") == null ?
                -1 : resultSet.getInt("payment_id");
        int driver_id =  resultSet.getObject("driver_id") == null ?
                -1 : resultSet.getInt("driver_id");
        int passenger_id = resultSet.getObject("passenger_id") == null ?
                -1 : resultSet.getInt("passenger_id");

        Address address = new AddressDataMapper().get(new Query("{'id':'" + address_id + "'}"));
        Payment payment = payment_id < 0 ? null :
                new PaymentDataMapper().get(new Query("{'id': '" + payment_id + "'}"));
        Driver driver = driver_id < 0 ? null :
                new DriverDataMapper().get(new Query("{'id': '" + driver_id + "'}"));
        Passenger passenger = passenger_id < 0 ? null :
                new PassengerDataMapper().get(new Query("{'id': '" + passenger_id + "'}"));

        return new Order(id, creationtime, status, address, payment, driver, passenger);
    }

    @Override
    String getTableName() {
        return "Order";
    }

    @Override
    public void insert(Order item) throws SQLException {
        String sql = "INSERT INTO \"" + getTableName() +
                "\" (creationtime, status, address_id, payment_id, passenger_id, driver_id) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement prepared = getConnection().prepareStatement(sql);
        prepared.setTimestamp(1, item.getCreationTime());
        prepared.setString(2, item.getStatus().getId());
        prepared.setInt(3, item.getAddress().getId());
        if(item.getPayment() != null) {
            prepared.setInt(4, item.getPayment().getId());
        }
        else {
            prepared.setNull(4, Types.INTEGER);
        }

        if(item.getPassenger() != null) {
            prepared.setInt(5, item.getPassenger().getId());
        }
        else {
            prepared.setNull(5, Types.INTEGER);
        }

        if(item.getDriver() != null) {
            prepared.setInt(6, item.getDriver().getId());
        }
        else {
            prepared.setNull(6, Types.INTEGER);
        }

        prepared.execute();
    }

    @Override
    public void update(Order item) throws SQLException {
        String sql = "UPDATE \"" + getTableName() +
                "\" SET creationtime=?, status=?, address_id=?, payment_id=?, passenger_id=?, driver_id=?;";
        PreparedStatement prepared = getConnection().prepareStatement(sql);
        prepared.setTimestamp(1, item.getCreationTime());
        prepared.setString(2, item.getStatus().getId());
        prepared.setInt(3, item.getAddress().getId());
        if(item.getPayment() == null) {
            prepared.setInt(4, item.getPayment().getId());
        }
        else {
            prepared.setNull(4, Types.INTEGER);
        }

        if(item.getPassenger() == null) {
            prepared.setInt(5, item.getPassenger().getId());
        }
        else {
            prepared.setNull(5, Types.INTEGER);
        }

        if(item.getDriver() == null) {
            prepared.setInt(6, item.getDriver().getId());
        }
        else {
            prepared.setNull(6, Types.INTEGER);
        }

        prepared.execute();
    }
}
