package main.database;

import main.common.Query;
import main.logic.Payment;
import main.logic.Rate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oglandx on 5/30/16.
 */
public class PaymentDataMapper extends SimpleTableDataMapper<Payment> {

    public PaymentDataMapper() throws SQLException {
        getConnection();
    }

    @Override
    Payment current(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int distance = resultSet.getInt("distance");
        int waitmin = resultSet.getInt("waitmin");
        int rate_id = resultSet.getObject("rate_id") == null ?
                -1 : resultSet.getInt("rate_id");

        Rate rate = new RateDataMapper().get(new Query("{'id': '" + rate_id + "'}"));
        return new Payment(id, waitmin, distance, rate);
    }

    @Override
    public void insert(Payment item) throws SQLException {
        String sql = "INSERT INTO \"" + getTableName() + "\" (distance, waitmin, rate_id) VALUES (?, ?, ?);";
        PreparedStatement prepared = getConnection().prepareStatement(sql);
        prepared.setInt(1, item.getDistance());
        prepared.setInt(2, item.getWaitMin());
        prepared.setInt(3, item.getRate().getId());

        prepared.execute();
    }

    @Override
    public void update(Payment item) throws SQLException {
        String sql = "UPDATE \"" + getTableName() + "\" SET distance=?, waitmin=?, rate_id=? WHERE id = ?;";
        PreparedStatement prepared = getConnection().prepareStatement(sql);
        prepared.setInt(1, item.getDistance());
        prepared.setInt(2, item.getWaitMin());
        prepared.setInt(3, item.getRate().getId());
        prepared.setInt(4, item.getId());

        prepared.execute();
    }

    @Override
    String getTableName() {
        return "payment";
    }
}
