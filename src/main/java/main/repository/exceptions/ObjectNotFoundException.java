package main.repository.exceptions;

/**
 * Created by oglandx on 5/23/16.
 */
public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(Throwable cause){
        super(cause);
    }
    public ObjectNotFoundException(){
        super();
    }
}
