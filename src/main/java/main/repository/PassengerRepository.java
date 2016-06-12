package main.repository;

import main.logic.Passenger;

/**
 * Created by oglandx on 5/22/16.
 */
public class PassengerRepository extends Repository<Passenger>{
    @Override
    public void update(Passenger item) {
        list.stream()
                .filter(entry -> entry.getId() == item.getId())
                .forEach(entry -> entry.register(item.getRegData()));
    }
}
