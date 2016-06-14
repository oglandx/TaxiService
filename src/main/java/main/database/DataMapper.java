package main.database;

import main.database.exceptions.SQLMultipleObjectsException;
import main.database.exceptions.SQLObjectNotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oglandx on 5/23/16.
 */
public abstract class DataMapper<T> implements AbstractDataMapper<T> {
    private Connection connection = null;

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DataSourceGateway.getConnection();
            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        return connection;
    }

    List<T> multiple(ResultSet resultSet) throws SQLException {
        List<T> results = new ArrayList<>();
        while(resultSet.next()){
            results.add(current(resultSet));
        }
        return results;
    }

    T single(ResultSet resultSet) throws SQLException {
        if(!resultSet.next()) {
            throw new SQLObjectNotFoundException();
        }
        T user = current(resultSet);
        if(resultSet.next()){
            throw new SQLMultipleObjectsException();
        }
        return user;
    }

    abstract T current(ResultSet resultSet) throws SQLException;


    @Override
    public void closeConnection() throws SQLException {
        if(connection != null){
            connection.close();
            connection = null;
        }
    }
}
