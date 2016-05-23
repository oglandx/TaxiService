package taxi.repository;

import taxi.logic.Operator;

/**
 * Created by oglandx on 5/23/16.
 */
public class OperatorRepository extends Repository<Operator> {
    @Override
    public void update(Operator item) {
        list.stream()
                .filter(entry -> entry.getId() == item.getId())
                .forEach(entry -> entry.register(item.getRegData()));
    }
}
