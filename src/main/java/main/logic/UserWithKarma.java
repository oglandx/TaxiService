package main.logic;

/**
 * Created by oglandx on 5/8/16.
 */
public class UserWithKarma extends User implements AbstractUserWithKarma {
    private int karma;

    @Override
    public int incKarma(int i) {
        return karma += i;
    }

    @Override
    public int decKarma(int i) {
        return karma += i;
    }

    @Override
    public void clearKarma() {
        karma = 0;
    }

    @Override
    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public UserWithKarma(){}
}
