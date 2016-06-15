package main.logic;

/**
 * Created by oglandx on 4/30/16.
 */
public interface AbstractUserWithKarma extends AbstractUser {
    int incKarma(int i);
    int decKarma(int i);
    void clearKarma();
    int getKarma();
}
