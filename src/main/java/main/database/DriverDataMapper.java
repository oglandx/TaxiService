package main.database;

import main.logic.DriverStatus;
import main.logic.RegisterData;
import main.logic.Driver;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by oglandx on 5/30/16.
 */
public class DriverDataMapper extends UserDataMapper<Driver> {

    protected String getTableName(){
        return "driver";
    }

    public Map<String, String> getDispatcher(){
        Map<String, String> dispatcher = new HashMap<>();
        dispatcher.put("status", getTableName());
        return dispatcher;
    }

    public DriverDataMapper() throws SQLException {
        getConnection();
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

        String sql = "SELECT * FROM \"" + getTableName() + "\" WHERE user_id = " + id + ";";
        resultSet = getConnection().createStatement().executeQuery(sql);
        DriverStatus status = resultSet.next() && resultSet.getObject("status") != null ?
                DriverStatus.valueOf(resultSet.getString("status")) : DriverStatus.FREE;
        id = resultSet.getInt("id");

        return new Driver(id, new RegisterData(lastname, firstname, middlename, birthdate, email, pass), karma, status);
    }

    @Override
    public void insert(Driver item) throws SQLException {
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
                "INSERT INTO \"" + getTableName() + "\"(user_id, status)" +
                        "VALUES((SELECT id FROM \"User\" WHERE email = ? "  +
                        "AND id not in (SELECT user_id FROM \"" + getTableName() + "\")), ?);";
        prepared = getConnection().prepareStatement(sql);
        prepared.setString(1, item.getRegData().getEmail());
        prepared.setString(2, item.getStatus().getId());
        prepared.execute();
    }

    @Override
    public void update(Driver item) throws SQLException {
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

        sql = "UPDATE \"" + getTableName() + "\" SET status = ? WHERE user_id = ?;";
        prepared = getConnection().prepareStatement(sql);
        prepared.setString(1, item.getStatus().getId());
        prepared.setInt(2, item.getId());
    }
}
