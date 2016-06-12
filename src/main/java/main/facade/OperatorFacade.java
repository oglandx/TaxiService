package main.facade;

import main.common.Query;
import main.database.OperatorDataMapper;
import main.logic.AuthData;
import main.logic.Operator;
import main.logic.RegisterData;
import main.repository.OperatorRepository;
import main.repository.exceptions.DatabaseException;
import main.repository.exceptions.MultipleObjectsException;
import main.repository.exceptions.ObjectNotFoundException;

import java.sql.SQLException;

/**
 * Created by oglandx on 6/12/16.
 */
public class OperatorFacade {
    private static OperatorRepository repository = null;
    private static OperatorFacade instance = null;

    private OperatorFacade() throws ApplicationError {
        if (repository == null) {
            OperatorDataMapper dataMapper = null;
            try {
                dataMapper = new OperatorDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            repository = new OperatorRepository(dataMapper);
        }
    }

    public static OperatorFacade getInstance() throws ApplicationError{
        if (instance == null) {
            instance = new OperatorFacade();
        }
        return instance;
    }

    public boolean registerNew(RegisterData regData) throws ApplicationError {
        String existenceQuery = "{'email': '" + regData.getEmail() + "'}";
        if(check(new Query(existenceQuery))) {
            return false;
        }

        Operator passenger = new Operator(regData);
        try {
            repository.create(passenger);
        }
        catch (DatabaseException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean registerNew(Operator operator) throws ApplicationError {
        return registerNew(operator.getRegData());
    }

    public boolean auth(AuthData authData) throws ApplicationError {
        String query = "{'email': '" + authData.getEmail() + "', 'pass': '" + authData.getPassHash() + "'}";
        return check(new Query(query));
    }

    public boolean auth(Operator operator) throws ApplicationError {
        return auth(operator.getRegData().getAuthData());
    }

    private boolean check(Query query) throws ApplicationError {
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

    public boolean closeConnection() {
        try {
            repository.closeDataMapperConnection();
        }
        catch (DatabaseException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
