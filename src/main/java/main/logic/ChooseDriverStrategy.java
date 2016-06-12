package main.logic;

import java.util.List;

/**
 * Created by oglandx on 5/16/16.
 */
public interface ChooseDriverStrategy {
    Driver solve(List<Driver> drivers);
}
