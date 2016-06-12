package main.database;

import main.logic.Passenger;
import main.logic.RegisterData;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Created by oglandx on 5/30/16.
 */
public class PassengerDataMapper extends UserWithKarmaDataMapper<Passenger> {

    protected String getTableName(){
        return "passenger";
    }

    public PassengerDataMapper() throws SQLException {
        try {
            connection = DataSourceGateway.getConnection();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    Passenger current(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");
        String middlename = resultSet.getString("middlename");
        Date birthdate = resultSet.getDate("birthdate");
        String email = resultSet.getString("email");
        String pass = resultSet.getString("pass");
        int karma = resultSet.getInt("karma");

        return new Passenger(id, new RegisterData(lastname, firstname, middlename, birthdate, email, pass), karma);
    }


    @Override
    public void closeConnection() throws SQLException {
        if(connection != null){
            connection.close();
            connection = null;
        }
    }
}
