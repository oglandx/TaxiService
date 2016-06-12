package main.database.exceptions;

import java.sql.SQLException;

/**
 * Created by oglandx on 5/23/16.
 */
public class SQLMultipleObjectsException extends SQLException {
    public SQLMultipleObjectsException(){
        super("On GET query got multiple objects");
    }
}
