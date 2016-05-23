package taxi.repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by oglandx on 5/22/16.
 */
public interface AbstractRepository<T> {
    List<T> getItems();
    T get(T item) throws MultipleObjectsException, ObjectNotFoundException;
    void create(T item);
    void update(T item);
    boolean remove(T item);
}
