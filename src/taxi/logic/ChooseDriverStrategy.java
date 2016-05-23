package taxi.logic;

/**
 * Created by oglandx on 5/16/16.
 */
public interface ChooseDriverStrategy {
    Driver solve(final Driver[] drivers);
}
