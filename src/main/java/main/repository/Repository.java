package main.repository;

import main.common.Query;
import main.logic.Entity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by oglandx on 5/23/16.
 */
public abstract class Repository<T extends Entity> implements AbstractRepository<T> {
    protected ArrayList<T> list = new ArrayList<>();

    @Override
    public List<T> all() {
        return this.list;
    }

    @Override
    public T get(Query query) throws MultipleObjectsException, ObjectNotFoundException {
        List<T> result = list.stream()
                .filter(query::check)
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

    @Override
    public List<T> filter(Query query) {
        return this.list.stream()
                .filter(query::check)
                .collect(Collectors.toList());
    }

}
