package main.repository;

import main.common.Query;
import main.database.AbstractDataMapper;
import main.database.exceptions.SQLObjectNotFoundException;
import main.logic.Entity;
import main.repository.exceptions.DatabaseException;
import main.repository.exceptions.MultipleObjectsException;
import main.repository.exceptions.ObjectNotFoundException;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by oglandx on 5/23/16.
 */
public abstract class Repository<T extends Entity> implements AbstractRepository<T> {
    protected List<T> list = new ArrayList<>();
    private AbstractDataMapper<T> dataMapper = null;
    private boolean dataMapperEnabled = false;

    Repository(AbstractDataMapper<T> dataMapper) {
        this.dataMapper = dataMapper;
        dataMapperEnabled = dataMapper != null;
    }

    public AbstractDataMapper<T> getDataMapper() {
        return dataMapperEnabled ? dataMapper : null;
    }

    @Override
    public List<T> all() throws DatabaseException {
        if (getDataMapper() != null) {
            try {
                this.list = dataMapper.all();
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
        return this.list;
    }

    @Override
    public T get(Query query) throws DatabaseException, MultipleObjectsException, ObjectNotFoundException {
        List<T> result = list.stream()
                .filter(query::check)
                .collect(Collectors.toList());
        if(result.size() > 1){
            throw new MultipleObjectsException();
        }
        if(result.size() < 1){
            if (getDataMapper() != null) {
                try {
                    result.add(getDataMapper().get(query));
                    list.add(result.get(0));
                } catch (SQLObjectNotFoundException e) {
                    throw new ObjectNotFoundException(e);
                } catch (SQLException e) {
                    throw new DatabaseException(e);
                }
            }
            else {
                throw new ObjectNotFoundException();
            }
        }
        return result.get(0);
    }

    @Override
    public void create(T item) throws DatabaseException {
        if (getDataMapper() != null) {
            try {
                getDataMapper().insert(item);
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
        else {
            list.add(item);
        }
    }

    @Override
    public boolean remove(Query query) throws DatabaseException {
        if (getDataMapper() != null) {
            try {
                getDataMapper().delete(query);
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
        return list.removeIf(query::check);
    }

    @Override
    public boolean remove(T item) throws DatabaseException {
        if (getDataMapper() != null) {
            try {
                getDataMapper().delete(new Query("{'id': '" + item.getId() + "'}"));
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
        return list.removeIf(entry -> entry.getId() == item.getId());
    }

    @Override
    public List<T> filter(Query query) throws DatabaseException {
        List<T> result;
        if (getDataMapper() == null){
            result = this.list.stream()
                    .filter(query::check)
                    .collect(Collectors.toList());
        }
        else {
            try {
                result = getDataMapper().filter(query);
            }
            catch (SQLException e){
                throw new DatabaseException(e);
            }
        }
        return result;
    }

    public void updateDb(T item) throws DatabaseException {
        if (getDataMapper() != null) {
            try {
                getDataMapper().update(item);
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
    }

    public void closeDataMapperConnection() throws DatabaseException {
        if (dataMapper != null) {
            try {
                dataMapper.closeConnection();
            }
            catch (SQLException e){
                throw new DatabaseException(e);
            }
        }
    }

    public void enableDataMapper(boolean value){
        dataMapperEnabled = value;
    }
}
