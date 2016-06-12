package main.logic;

/**
 * Created by oglandx on 5/8/16.
 */
public class UserWithKarma extends User implements AbstractUserWithKarma {
    private int karma;

    @Override
    public int incKarma() {
        return ++karma;
    }

    @Override
    public int decKarma() {
        return --karma;
    }

    @Override
    public void clearKarma() {
        karma = 0;
    }

    @Override
    public int getKarma() {
        return karma;
    }

    protected void setKarma(int karma) {
        this.karma = karma;
    }

    public UserWithKarma(){}
}
