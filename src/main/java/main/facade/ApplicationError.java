package main.facade;

/**
 * Created by oglandx on 6/12/16.
 */
public class ApplicationError extends Exception {
    public ApplicationError(Throwable e) {
        super(e);
    }
}
