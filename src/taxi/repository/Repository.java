package taxi.repository;

import taxi.logic.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oglandx on 5/23/16.
 */
public abstract class Repository<T extends Entity> implements AbstractRepository<T> {
    ArrayList<T> list = new ArrayList<>();

    @Override
    public List<T> getItems() {
        return this.list;
    }

    @Override
    public T get(T item) throws MultipleObjectsException, ObjectNotFoundException {
        List<T> result = list.stream()
                .filter(entry -> entry.getId() == item.getId())
                .collect(Collectors.toList());
        if(result.size() > 1){
            throw new MultipleObjectsException();
        }
        if(result.size() < 1){
            throw new ObjectNotFoundException();
        }
        return result.get(0);
    }

    @Override
    public void create(T item) {
        list.add(item);
    }

    @Override
    public boolean remove(T item) {
        return list.removeIf(entry -> entry.getId() == item.getId());
    }
}
