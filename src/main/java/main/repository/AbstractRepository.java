package main.repository;

import main.common.Query;
import main.repository.exceptions.DatabaseException;
import main.repository.exceptions.MultipleObjectsException;
import main.repository.exceptions.ObjectNotFoundException;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by oglandx on 5/22/16.
 */
public interface AbstractRepository<T> {
    List<T> all() throws DatabaseException;
    T get(Query query) throws DatabaseException, MultipleObjectsException, ObjectNotFoundException;
    void create(T item) throws DatabaseException;
    void update(T item) throws DatabaseException;
    boolean remove(Query query) throws DatabaseException;
    boolean remove(T item) throws DatabaseException;
    List<T> filter(Query query) throws DatabaseException;
}
