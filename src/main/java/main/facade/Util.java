package main.facade;

import main.common.Query;
import main.logic.Entity;
import main.repository.Repository;
import main.repository.exceptions.DatabaseException;
import main.repository.exceptions.MultipleObjectsException;
import main.repository.exceptions.ObjectNotFoundException;

/**
 * Created by oglandx on 6/12/16.
 */
public class Util {
    private Util(){}

    public static boolean checkQuery(Repository repository, Query query) throws ApplicationError {
        try {
            repository.get(query);
        }
        catch (MultipleObjectsException | DatabaseException e){
            e.printStackTrace();
            throw new ApplicationError(e);
        }
        catch (ObjectNotFoundException e){
            return false;
        }
        return true;
    }

    public static<T extends Entity> T extract(Repository<T> repository, Query query) throws ApplicationError {
        T result;
        try {
            result = repository.get(query);
        }
        catch (MultipleObjectsException | DatabaseException e){
            e.printStackTrace();
            throw new ApplicationError(e);
        }
        catch (ObjectNotFoundException e){
            result = null;
        }
        return result;
    }
}
