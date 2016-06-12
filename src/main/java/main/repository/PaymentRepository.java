package main.repository;

import main.database.PaymentDataMapper;
import main.logic.Payment;

/**
 * Created by oglandx on 5/23/16.
 */
public class PaymentRepository extends Repository<Payment> {

    @Override
    public void update(Payment item) {
        list.stream()
                .filter(entry -> entry.getId() == item.getId())
                .forEach(entry -> {
                    entry.setDistance(item.getDistance());
                    entry.setWaitMin(item.getWaitMin());
                });
    }

    public PaymentRepository(PaymentDataMapper dataMapper){
        super(dataMapper);
    }

    public PaymentRepository(){
        super(null);
    }
}
