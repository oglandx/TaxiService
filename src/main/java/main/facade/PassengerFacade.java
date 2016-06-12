package main.facade;

import main.common.Query;
import main.database.PassengerDataMapper;
import main.logic.AuthData;
import main.logic.Passenger;
import main.logic.RegisterData;
import main.repository.PassengerRepository;
import main.repository.exceptions.DatabaseException;
import main.repository.exceptions.MultipleObjectsException;
import main.repository.exceptions.ObjectNotFoundException;

import java.sql.SQLException;

/**
 * Created by oglandx on 6/12/16.
 */
public class PassengerFacade {
    private static PassengerRepository repository = null;
    private static PassengerFacade instance = null;

    private PassengerFacade() throws ApplicationError {
        if (repository == null) {
            PassengerDataMapper dataMapper = null;
            try {
                dataMapper = new PassengerDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            repository = new PassengerRepository(dataMapper);
        }
    }

    public static PassengerFacade getInstance() throws ApplicationError {
        if (instance == null) {
            instance = new PassengerFacade();
        }
        return instance;
    }

    public boolean registerNew(RegisterData regData) throws ApplicationError {
        String existenceQuery = "{'email': '" + regData.getEmail() + "'}";
        if(Util.checkQuery(repository, new Query(existenceQuery))) {
            return false;
        }

        Passenger passenger = new Passenger(regData);
        try {
            repository.create(passenger);
        }
        catch (DatabaseException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean registerNew(Passenger passenger) throws ApplicationError {
        return registerNew(passenger.getRegData());
    }

    public boolean auth(AuthData authData) throws ApplicationError {
        String query = "{'email': '" + authData.getEmail() + "', 'pass': '" + authData.getPassHash() + "'}";
        return Util.checkQuery(repository, new Query(query));
    }

    public boolean auth(Passenger passenger) throws ApplicationError {
        return auth(passenger.getRegData().getAuthData());
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
