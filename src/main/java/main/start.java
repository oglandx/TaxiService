package main;

import main.facade.ApplicationError;
import main.facade.DriverFacade;
import main.facade.OperatorFacade;
import main.facade.PassengerFacade;
import main.logic.*;
import main.repository.PassengerRepository;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by oglandx on 6/5/16.
 */
public class start {
    public static void main(final String[] args){
        try {
//            PassengerFacade facade = PassengerFacade.getInstance();
//            Passenger passenger = facade.auth(new AuthData("some@mail.com", "pwd"));
//            facade.createOrder(passenger, new Address("Мусохранск", "Иосифа Кобзона", "28А"));
//            for(Order order: facade.getOrderList(passenger, OrderStatus.NEW)){
//                System.out.println(order.getPassenger().getRegData().getLastName());
//            }
//            facade.closeConnection();

            DriverFacade driverFacade = DriverFacade.getInstance();
            driverFacade.registerNew(new RegisterData(
                    "Водилов", "Иван", "Иванович", new Date(Calendar.getInstance().getTime().getTime()), "driver@gmail.com", "123"
            ));
            Driver driver = driverFacade.auth(new AuthData("driver@gmail.com", "123"));


            OperatorFacade facade = OperatorFacade.getInstance();
            List<Order> orders = facade.getOrderList();
            List<Driver> drivers = facade.getFreeDrivers();
            drivers = drivers;
        }
        catch (ApplicationError e){
            e.printStackTrace();
        }

    }
}
