package main.database;

import main.logic.Passenger;
import main.logic.RegisterData;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Created by oglandx on 5/30/16.
 */
public class PassengerDataMapper extends UserDataMapper<Passenger> {

    protected String getTableName(){
        return "passenger";
    }

    public PassengerDataMapper() throws SQLException {
        getConnection();
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
    public void insert(Passenger item) throws SQLException {
        String sql =
                "INSERT INTO \"User\" (lastname, firstname, middlename, birthdate, email, pass, karma)"  +
                        "VALUES (?, ?, ?, ?, ?, ?, ?); ";
        PreparedStatement prepared = getConnection().prepareStatement(sql);
        prepared.setString(1, item.getRegData().getLastName());
        prepared.setString(2, item.getRegData().getFirstName());
        prepared.setString(3, item.getRegData().getMiddleName());
        prepared.setDate(4, item.getRegData().getBirthDate());
        prepared.setString(5, item.getRegData().getEmail());
        prepared.setString(6, item.getRegData().getPassHash());
        prepared.setInt(7, item.getKarma());

        prepared.execute();

        sql =
                "INSERT INTO \"" + getTableName() + "\"(user_id) " +
                        "(SELECT id FROM \"User\" WHERE email = ? " +
                        "AND id not in (SELECT user_id FROM \"" + getTableName() + "\")); ";

        prepared = getConnection().prepareStatement(sql);
        prepared.setString(1, item.getRegData().getEmail());
        prepared.execute();
    }

    @Override
    public void update(Passenger item) throws SQLException {
        String sql =
                "UPDATE \"User\" " +
                        "SET lastname = ?, firstname = ?, middlename = ?, birthdate = ?, email = ?, pass = ?, " +
                        "karma = ? WHERE id = ?; ";
        PreparedStatement prepared = getConnection().prepareStatement(sql);
        prepared.setString(1, item.getRegData().getLastName());
        prepared.setString(2, item.getRegData().getFirstName());
        prepared.setString(3, item.getRegData().getMiddleName());
        prepared.setDate(4, item.getRegData().getBirthDate());
        prepared.setString(5, item.getRegData().getEmail());
        prepared.setString(6, item.getRegData().getPassHash());
        prepared.setInt(7, item.getKarma());
        prepared.setInt(8, item.getId());

        prepared.execute();
    }
}
