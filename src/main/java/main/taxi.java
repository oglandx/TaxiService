package main;

import main.database.PassengerDataMapper;
import main.logic.Passenger;
import main.logic.RegisterData;
import main.common.Query;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by oglandx on 6/5/16.
 */
public class taxi {
    public static void main(final String[] args){
        try{
            Date date = new Date(Calendar.getInstance().getTime().getTime());
            Passenger passenger = new Passenger(
                    new RegisterData("Новый", "Иван", "Олоевич", date, "uuumail@mail.mail", "hhh"));
            passenger.setDataMapper(new PassengerDataMapper());
            passenger.save();
            passenger.getDataMapper().closeConnection();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
}
