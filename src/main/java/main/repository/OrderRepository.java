package main.repository;

import main.database.OrderDataMapper;
import main.logic.Order;
import main.repository.exceptions.DatabaseException;

/**
 * Created by oglandx on 5/23/16.
 */
public class OrderRepository extends Repository<Order> {

    @Override
    public void update(Order item) throws DatabaseException {
        updateDb(item);
        list.stream()
                .filter(entry -> entry.getId() == item.getId())
                .forEach(entry -> {
                    entry.bindDriver(item.getDriver());
                    entry.assignPassenger(item.getPassenger());
                });
    }

    public OrderRepository(OrderDataMapper dataMapper){
        super(dataMapper);
    }

    public OrderRepository(){
        super(null);
    }
}
