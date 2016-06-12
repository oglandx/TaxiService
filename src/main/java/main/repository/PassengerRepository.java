package main.repository;

import main.database.AbstractDataMapper;
import main.database.PassengerDataMapper;
import main.logic.Passenger;
import main.repository.exceptions.DatabaseException;

/**
 * Created by oglandx on 5/22/16.
 */
public class PassengerRepository extends Repository<Passenger>{
    @Override
    public void update(Passenger item) throws DatabaseException {
        updateDb(item);
        list.stream()
                .filter(entry -> entry.getId() == item.getId())
                .forEach(entry -> entry.register(item.getRegData()));
    }

    public PassengerRepository(PassengerDataMapper dataMapper){
        super(dataMapper);
    }

    public PassengerRepository(){
        super(null);
    }
}
