package main.repository.exceptions;

/**
 * Created by oglandx on 5/23/16.
 */
public class MultipleObjectsException extends Exception {
    public MultipleObjectsException(Throwable cause){
        super(cause);
    }
    public MultipleObjectsException(){
        super();
    }
}
