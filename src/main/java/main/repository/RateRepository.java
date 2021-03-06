package main.repository;

import main.database.RateDataMapper;
import main.logic.Rate;
import main.repository.exceptions.DatabaseException;

import java.sql.SQLException;

/**
 * Created by oglandx on 6/6/16.
 */
public class RateRepository extends Repository<Rate> {

    @Override
    public void update(Rate item) throws DatabaseException {
        updateDb(item);
        list.stream()
                .filter(entry -> entry.getId() == item.getId())
                .forEach(entry -> {
                    entry.setCostPerKm(item.getCostPerKm());
                    entry.setCostPerMin(item.getCostPerMin());
                    entry.setFreeMinutes(item.getFreeMinutes());
                });
    }

    public RateRepository(RateDataMapper dataMapper){
        super(dataMapper);
    }

    public RateRepository(){
        super(null);
    }
}