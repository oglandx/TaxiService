package main.database;

import main.logic.UserWithKarma;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by oglandx on 6/5/16.
 */
public abstract class UserWithKarmaDataMapper<T extends UserWithKarma> extends UserDataMapper<T> {

    @Override
    public void insert(T item) throws SQLException {
        String sql =
                "INSERT INTO \"User\" (lastname, firstname, middlename, birthdate, email, pass, karma)"  +
                        "VALUES (?, ?, ?, ?, ?, ?, ?); ";
        PreparedStatement prepared = connection.prepareStatement(sql);
        prepared.setString(1, item.getRegData().getLastName());
        prepared.setString(2, item.getRegData().getFirstName());
        prepared.setString(3, item.getRegData().getMiddleName());
        prepared.setDate(4, item.getRegData().getBirthDate());
        prepared.setString(5, item.getRegData().getEmail());
        prepared.setString(6, item.getRegData().getPassHash());
        prepared.setInt(7, item.getKarma());

        prepared.execute();

        //TODO currval(pg_get_serial_sequence('User','id'));

        sql =
                "INSERT INTO \"" + getTableName() + "\"(user_id)" +
                        "(SELECT id FROM \"User\" WHERE lastname=? AND firstname=? AND middlename=? AND birthdate=? " +
                        "AND email=? AND pass=? AND karma=? AND id not in (SELECT user_id FROM passenger));";
        prepared = connection.prepareStatement(sql);
        prepared.setString(1, item.getRegData().getLastName());
        prepared.setString(2, item.getRegData().getFirstName());
        prepared.setString(3, item.getRegData().getMiddleName());
        prepared.setDate(4, item.getRegData().getBirthDate());
        prepared.setString(5, item.getRegData().getEmail());
        prepared.setString(6, item.getRegData().getPassHash());
        prepared.setInt(7, item.getKarma());

        prepared.execute();
    }

    @Override
    public void update(T item) throws SQLException {
        String sql =
                "UPDATE \"User\" " +
                        "SET lastname = ?, firstname = ?, middlename = ?, birthdate = ?, email = ?, pass = ?, karma = ?"  +
                        "WHERE id = ?; ";
        PreparedStatement prepared = connection.prepareStatement(sql);
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
