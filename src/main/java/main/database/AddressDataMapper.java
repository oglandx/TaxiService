package main.database;

import main.logic.Address;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oglandx on 6/6/16.
 */
public class AddressDataMapper extends SimpleTableDataMapper<Address> {

    public AddressDataMapper() throws SQLException {
        try {
            connection = DataSourceGateway.getConnection();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "address";
    }

    @Override
    Address current(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String city = resultSet.getString("city");
        String street = resultSet.getString("street");
        String building = resultSet.getString("building");
        return new Address(id, city, street, building);
    }

    @Override
    public void insert(Address item) throws SQLException {
        String sql = "INSERT INTO \"" + getTableName() + "\" (city, street, building) VALUES (?, ?, ?)";
        PreparedStatement prepared = connection.prepareStatement(sql);
        prepared.setString(1, item.getCity());
        prepared.setString(2, item.getStreet());
        prepared.setString(3, item.getBuilding());

        prepared.execute();
    }

    @Override
    public void update(Address item) throws SQLException {
        String sql = "UPDATE \"" + getTableName() + "\" SET city=?, street=?, building=?;";
        PreparedStatement prepared = connection.prepareStatement(sql);
        prepared.setString(1, item.getCity());
        prepared.setString(2, item.getStreet());
        prepared.setString(3, item.getBuilding());

        prepared.execute();
    }
}
