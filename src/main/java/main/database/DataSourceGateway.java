package main.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by oglandx on 5/30/16.
 */
public class DataSourceGateway {
    private static Connection connection = null;

    public static final String DATABASE = "taxiservice";
    public static final String HOST = "localhost";
    public static final String PORT = "5432";
    public static final String JDBC = "postgresql";
    public static final String USERNAME = "oglandx";
    public static final String PASSWORD = "taximasterpass";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if(connection == null || connection.isClosed()){
            Class.forName("org.postgresql.Driver");
            final String url = "jdbc:" + JDBC + "://" + HOST + ":" + PORT + "/" + DATABASE;
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        connection = null;
    }
}
