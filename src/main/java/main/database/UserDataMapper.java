package main.database;

import main.database.exceptions.SQLMultipleObjectsException;
import main.logic.User;
import main.common.Query;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by oglandx on 6/5/16.
 */
public abstract class UserDataMapper<T extends User> extends DataMapper<T> {

    abstract String getTableName();

    /**
     * enables to dispatch queries between tables
     * fields from default table ("User") must not be defined or defined as null
     */
    public Map<String, String> getDispatcher() {
        return null;
    }

    @Override
    public T get(Query query) throws SQLException {
        Statement statement = getConnection().createStatement();
        String sql =
                "SELECT * FROM \"User\" " +
                        "WHERE \"id\" IN (" +
                            "SELECT \"user_id\" FROM \"" + getTableName() +
                            "\" WHERE " + query.sql(getDispatcher(), getTableName()) +
                        ") AND " + query.sql(getDispatcher(), null) +  ";";
        ResultSet resultSet = statement.executeQuery(sql);
        return single(resultSet);
    }

    @Override
    public List<T> filter(Query query) throws SQLException {
        Statement statement = getConnection().createStatement();
        String sql =
                "SELECT * FROM \"User\" " +
                        "WHERE \"id\" IN (" +
                            "SELECT \"user_id\" FROM \"" + getTableName() +
                            "\" WHERE " + query.sql(getDispatcher(), getTableName()) +
                        ") AND " + query.sql(getDispatcher(), null) +  ";";
        ResultSet resultSet = statement.executeQuery(sql);
        return multiple(resultSet);
    }

    @Override
    public List<T> all() throws SQLException {
        Statement statement = getConnection().createStatement();
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
                "WHERE user_id in (SELECT id FROM \"User\" WHERE" + query.sql(getDispatcher(), null) + ") AND " +
                        query.sql(getDispatcher(), getTableName()) + ";" +
                "DELETE FROM \"User\" WHERE " + query.sql(getDispatcher(), null) + "; ";
        PreparedStatement prepared = getConnection().prepareStatement(sql);
        prepared.execute();
    }
}