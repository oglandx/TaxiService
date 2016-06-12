package main;

import main.facade.ApplicationError;
import main.facade.DriverFacade;
import main.facade.OperatorFacade;
import main.facade.PassengerFacade;
import main.logic.*;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by oglandx on 6/5/16.
 */
public class start {
    public static void main(final String[] args){
        try {
            OperatorFacade facade = OperatorFacade.getInstance();
            System.out.println(facade.registerNew(
                    new Operator(
                            new RegisterData("Операторов", "Имя", "Отчество",
                                    new Date(Calendar.getInstance().getTime().getTime()), "operator@mail.com", "pwd")
                    )
            ));
            System.out.println(facade.auth(new AuthData("operator@mail.com", "pwd")));
        }
        catch (ApplicationError e){
            e.printStackTrace();
        }

    }
}
