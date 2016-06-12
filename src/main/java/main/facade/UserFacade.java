package main.facade;

import main.logic.AuthData;
import main.logic.RegisterData;

/**
 * Created by oglandx on 6/12/16.
 */
public interface UserFacade<T> {
    boolean registerNew(RegisterData regData) throws ApplicationError;
    boolean registerNew(T user) throws ApplicationError;
    boolean auth(AuthData authData) throws ApplicationError;
    boolean auth(T user) throws ApplicationError;
    boolean closeConnection();
}
