package main.database;

import main.logic.Operator;
import main.logic.RegisterData;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oglandx on 5/30/16.
 */
public class OperatorDataMapper extends UserDataMapper<Operator> {

    public OperatorDataMapper() throws SQLException {
        getConnection();
    }

    @Override
    String getTableName() {
        return "operator";
    }

    @Override
    Operator current(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");
        String middlename = resultSet.getString("middlename");
        Date birthdate = resultSet.getDate("birthdate");
        String email = resultSet.getString("email");
        String pass = resultSet.getString("pass");

        return new Operator(id, new RegisterData(lastname, firstname, middlename, birthdate, email, pass));
    }

    @Override
    public void insert(Operator item) throws SQLException {
        String sql =
                "INSERT INTO \"User\" (lastname, firstname, middlename, birthdate, email, pass)"  +
                        "VALUES (?, ?, ?, ?, ?, ?); ";
        PreparedStatement prepared = getConnection().prepareStatement(sql);
        prepared.setString(1, item.getRegData().getLastName());
        prepared.setString(2, item.getRegData().getFirstName());
        prepared.setString(3, item.getRegData().getMiddleName());
        prepared.setDate(4, item.getRegData().getBirthDate());
        prepared.setString(5, item.getRegData().getEmail());
        prepared.setString(6, item.getRegData().getPassHash());

        prepared.execute();

        sql =
                "INSERT INTO \"" + getTableName() + "\"(user_id)" +
                        "(SELECT id FROM \"User\" WHERE lastname=? AND firstname=? AND middlename=? AND birthdate=? " +
                        "AND email=? AND pass=? AND id not in (SELECT user_id FROM passenger));";
        prepared = getConnection().prepareStatement(sql);
        prepared.setString(1, item.getRegData().getLastName());
        prepared.setString(2, item.getRegData().getFirstName());
        prepared.setString(3, item.getRegData().getMiddleName());
        prepared.setDate(4, item.getRegData().getBirthDate());
        prepared.setString(5, item.getRegData().getEmail());
        prepared.setString(6, item.getRegData().getPassHash());

        prepared.execute();
    }

    @Override
    public void update(Operator item) throws SQLException {
        String sql =
                "UPDATE \"User\" " +
                        "SET lastname = ?, firstname = ?, middlename = ?, birthdate = ?, email = ?, pass = ?"  +
                        "WHERE id = ?; ";
        PreparedStatement prepared = getConnection().prepareStatement(sql);
        prepared.setString(1, item.getRegData().getLastName());
        prepared.setString(2, item.getRegData().getFirstName());
        prepared.setString(3, item.getRegData().getMiddleName());
        prepared.setDate(4, item.getRegData().getBirthDate());
        prepared.setString(5, item.getRegData().getEmail());
        prepared.setString(6, item.getRegData().getPassHash());
        prepared.setInt(7, item.getId());

        prepared.execute();
    }
}
