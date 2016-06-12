package main.repository;

import main.common.Query;

import java.util.List;

/**
 * Created by oglandx on 5/22/16.
 */
public interface AbstractRepository<T> {
    List<T> all();
    T get(Query query) throws MultipleObjectsException, ObjectNotFoundException;
    void create(T item);
    void update(T item);
    boolean remove(T item);
    List<T> filter(Query query);
}
