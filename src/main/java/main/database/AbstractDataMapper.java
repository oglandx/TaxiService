package main.database;

import main.common.Query;
import main.database.exceptions.SQLMultipleObjectsException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by oglandx on 5/23/16.
 */
public interface AbstractDataMapper<T> {
    T get(Query query) throws SQLException, SQLMultipleObjectsException;
    List<T> filter(Query query) throws SQLException;
    List<T> all() throws SQLException;
    void insert(T item) throws SQLException;
    void update(T item) throws SQLException;
    void delete(Query query) throws SQLException;
    void closeConnection() throws SQLException;
}
