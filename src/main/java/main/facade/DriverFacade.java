package main.facade;

import main.common.Query;
import main.database.DriverDataMapper;
import main.logic.AuthData;
import main.logic.Driver;
import main.logic.RegisterData;
import main.repository.DriverRepository;
import main.repository.exceptions.DatabaseException;
import main.repository.exceptions.MultipleObjectsException;
import main.repository.exceptions.ObjectNotFoundException;

import java.sql.SQLException;

/**
 * Created by oglandx on 6/12/16.
 */
public class DriverFacade  {
    private static DriverRepository repository = null;
    private static DriverFacade instance = null;

    private DriverFacade() throws ApplicationError {
        if (repository == null) {
            DriverDataMapper dataMapper = null;
            try {
                dataMapper = new DriverDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            repository = new DriverRepository(dataMapper);
        }
    }

    public static DriverFacade getInstance() throws ApplicationError {
        if (instance == null) {
            instance = new DriverFacade();
        }
        return instance;
    }

    public boolean registerNew(RegisterData regData) throws ApplicationError {
        String existenceQuery = "{'email': '" + regData.getEmail() + "'}";
        if(Util.checkQuery(repository, new Query(existenceQuery))) {
            return false;
        }

        Driver driver = new Driver(regData);
        try {
            repository.create(driver);
        }
        catch (DatabaseException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean registerNew(Driver passenger) throws ApplicationError {
        return registerNew(passenger.getRegData());
    }

    public boolean auth(AuthData authData) throws ApplicationError {
        String query = "{'email': '" + authData.getEmail() + "', 'pass': '" + authData.getPassHash() + "'}";
        return Util.checkQuery(repository, new Query(query));
    }

    public boolean auth(Driver driver) throws ApplicationError {
        return auth(driver.getRegData().getAuthData());
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
