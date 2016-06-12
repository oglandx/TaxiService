package main.database;

import main.logic.Rate;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oglandx on 5/30/16.
 */
public class RateDataMapper extends SimpleTableDataMapper<Rate> {

    public RateDataMapper() throws SQLException {
        try {
            connection = DataSourceGateway.getConnection();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    Rate current(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        BigDecimal costPerKm = resultSet.getBigDecimal("costperkm");
        BigDecimal costPerMin = resultSet.getBigDecimal("costpermin");
        int freeMinutes = resultSet.getInt("freeminutes");

        return new Rate(id, costPerKm, costPerMin, freeMinutes);
    }

    @Override
    public void insert(Rate item) throws SQLException {
        String sql = "INSERT INTO \"" + getTableName() + "\" (costperkm, costpermin, freeminutes) VALUES (?, ?, ?);";
        PreparedStatement prepared = connection.prepareStatement(sql);
        prepared.setBigDecimal(1, item.getCostPerKm());
        prepared.setBigDecimal(2, item.getCostPerMin());
        prepared.setInt(3, item.getFreeMinutes());

        prepared.execute();
    }

    @Override
    public void update(Rate item) throws SQLException {
        String sql = "UPDATE \"" + getTableName() + "\" SET costperkm=?, costpermin=?, freeminutes=?;";
        PreparedStatement prepared = connection.prepareStatement(sql);
        prepared.setBigDecimal(1, item.getCostPerKm());
        prepared.setBigDecimal(2, item.getCostPerMin());
        prepared.setInt(3, item.getFreeMinutes());

        prepared.execute();
    }

    @Override
    String getTableName() {
        return "rate";
    }
}
