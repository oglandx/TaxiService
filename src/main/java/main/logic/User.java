package main.logic;

import java.util.Date;

/**
 * Created by oglandx on 5/8/16.
 */
public class User extends Entity implements AbstractUser {
    private RegisterData regData = null;

    public RegisterData getRegData() {
        return regData;
    }

    @Override
    public long register(final RegisterData regData) {
        this.regData = regData;
        return getId();
    }

    @Override
    public boolean auth(final AuthData authData) {
        return this.regData.getEmail().equals(authData.getEmail()) &&
                this.regData.getPassHash().equals(authData.getPassHash());
    }

    @Override
    public boolean isRegistered() {
        return regData != null;
    }

    public User(){}
}
