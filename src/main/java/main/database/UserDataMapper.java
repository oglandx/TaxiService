package main.database;

import main.database.exceptions.SQLMultipleObjectsException;
import main.logic.User;
import main.common.Query;

import java.sql.*;
import java.util.List;

/**
 * Created by oglandx on 6/5/16.
 */
public abstract class UserDataMapper<T extends User> extends DataMapper<T> {

    abstract String getTableName();

    @Override
    public T get(Query query) throws SQLException, SQLMultipleObjectsException {
        Statement statement = connection.createStatement();
        String sql =
                "SELECT * FROM \"User\" " +
                        "WHERE \"id\" IN (SELECT \"user_id\" FROM \"" + getTableName() + "\") " +
                        "AND " + query.sql() +  ";";
        ResultSet resultSet = statement.executeQuery(sql);
        return single(resultSet);
    }

    @Override
    public List<T> filter(Query query) throws SQLException {
        Statement statement = connection.createStatement();
        String sql =
                "SELECT * FROM \"User\" " +
                        "WHERE \"id\" IN (SELECT \"user_id\" FROM \"" + getTableName() + "\") " +
                        " AND " + query.sql() +  ";";
        ResultSet resultSet = statement.executeQuery(sql);
        return multiple(resultSet);
    }

    @Override
    public List<T> all() throws SQLException {
        Statement statement = connection.createStatement();
        String sql =
                "SELECT * FROM \"User\" " +
                "WHERE \"id\" IN (SELECT \"user_id\" FROM \"" + getTableName() + "\");";
        ResultSet resultSet = statement.executeQuery(sql);
        return multiple(resultSet);
    }

    @Override
    public void delete(Query query) throws SQLException {
        String sql =
                "DELETE FROM \"" + getTableName() + "\" " +
                "WHERE user_id in (SELECT id FROM \"User\" WHERE" + query.sql() + ");" +
                "DELETE FROM \"User\" WHERE " + query.sql() + "; ";
        PreparedStatement prepared = connection.prepareStatement(sql);
        prepared.execute();
    }
}