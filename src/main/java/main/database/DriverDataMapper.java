package main.database;

import main.logic.RegisterData;
import main.logic.Driver;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Created by oglandx on 5/30/16.
 */
public class DriverDataMapper extends UserWithKarmaDataMapper<Driver> {

    protected String getTableName(){
        return "driver";
    }

    public DriverDataMapper() throws SQLException {
        try {
            connection = DataSourceGateway.getConnection();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    Driver current(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");
        String middlename = resultSet.getString("middlename");
        Date birthdate = resultSet.getDate("birthdate");
        String email = resultSet.getString("email");
        String pass = resultSet.getString("pass");
        int karma = resultSet.getInt("karma");

        return new Driver(id, new RegisterData(lastname, firstname, middlename, birthdate, email, pass), karma);
    }
}
