package main.facade;

import main.logic.AuthData;
import main.logic.RegisterData;

/**
 * Created by oglandx on 6/12/16.
 */
public interface UserFacade<T> {
    T registerNew(RegisterData regData) throws ApplicationError;
    T registerNew(T user) throws ApplicationError;
    T auth(AuthData authData) throws ApplicationError;
    T auth(T user) throws ApplicationError;
    boolean closeConnection();
}
