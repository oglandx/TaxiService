package main.database.exceptions;

import java.sql.SQLException;

/**
 * Created by oglandx on 5/23/16.
 */
public class SQLObjectNotFoundException extends SQLException {
    public SQLObjectNotFoundException(){
        super("Object not found");
    }
}
