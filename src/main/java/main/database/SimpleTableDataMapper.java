package main.database;

import main.database.exceptions.SQLMultipleObjectsException;
import main.logic.Entity;
import main.common.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by oglandx on 6/6/16.
 */
public abstract class SimpleTableDataMapper<T extends Entity> extends DataMapper<T> {
    abstract String getTableName();

    @Override
    public T get(Query query) throws SQLException, SQLMultipleObjectsException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM \"" + getTableName() + "\" WHERE " + query.sql() +  ";";
        ResultSet resultSet = statement.executeQuery(sql);
        return single(resultSet);
    }

    @Override
    public List<T> filter(Query query) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM \"" + getTableName() + "\" WHERE " + query.sql() +  ";";
        ResultSet resultSet = statement.executeQuery(sql);
        return multiple(resultSet);
    }

    @Override
    public List<T> all() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM \"" + getTableName() + "\";";
        ResultSet resultSet = statement.executeQuery(sql);
        return multiple(resultSet);
    }

    @Override
    public void delete(Query query) throws SQLException {
        String sql = "DELETE FROM \"" + getTableName() + "\" WHERE " + query.sql() + ";";
        PreparedStatement prepared = connection.prepareStatement(sql);
        prepared.execute();
    }
}
