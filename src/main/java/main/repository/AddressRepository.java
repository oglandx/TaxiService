package main.repository;

import main.database.AddressDataMapper;
import main.database.PaymentDataMapper;
import main.logic.Address;
import main.repository.exceptions.DatabaseException;

/**
 * Created by oglandx on 6/12/16.
 */
public class AddressRepository extends Repository<Address> {

    @Override
    public void update(Address item) throws DatabaseException {
        updateDb(item);
        list.stream()
                .filter(entry -> entry.getId() == item.getId())
                .forEach(entry -> {
                    entry.setBuilding(item.getBuilding());
                    entry.setStreet(item.getStreet());
                    entry.setCity(item.getCity());
                });
    }

    public AddressRepository(AddressDataMapper dataMapper){
        super(dataMapper);
    }

    public AddressRepository(){
        super(null);
    }
}
