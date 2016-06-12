package main.logic;

/**
 * Created by oglandx on 3/12/16.
 */
public interface AbstractUser {
    long register(final RegisterData regData);
    boolean auth(final AuthData authData);
    boolean isRegistered();
}
