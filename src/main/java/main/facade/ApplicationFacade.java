package main.facade;

import main.database.*;
import main.repository.*;

import java.sql.SQLException;

/**
 * Created by oglandx on 6/14/16.
 */
public class ApplicationFacade {
    private static PassengerFacade passengerFacade = null;
    private static OperatorFacade operatorFacade = null;
    private static DriverFacade driverFacade = null;

    private static PassengerRepository passengerRepository = null;
    private static OrderRepository orderRepository = null;
    private static AddressRepository addressRepository = null;
    private static DriverRepository driverRepository = null;
    private static RateRepository rateRepository = null;
    private static PaymentRepository paymentRepository = null;
    private static OperatorRepository operatorRepository = null;

    private static ApplicationFacade instance = null;

    private ApplicationFacade() throws ApplicationError {
        if (passengerRepository == null) {
            PassengerDataMapper dataMapper = null;
            try {
                dataMapper = new PassengerDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            passengerRepository = new PassengerRepository(dataMapper);
        }
        if (operatorRepository == null) {
            OperatorDataMapper dataMapper = null;
            try {
                dataMapper = new OperatorDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            operatorRepository = new OperatorRepository(dataMapper);
        }
        if (driverRepository == null) {
            DriverDataMapper dataMapper = null;
            try {
                dataMapper = new DriverDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            driverRepository = new DriverRepository(dataMapper);
        }
        if(orderRepository == null){
            OrderDataMapper dataMapper = null;
            try {
                dataMapper = new OrderDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            orderRepository = new OrderRepository(dataMapper);
        }
        if(addressRepository == null){
            AddressDataMapper dataMapper = null;
            try {
                dataMapper = new AddressDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            addressRepository = new AddressRepository(dataMapper);
        }
        if(rateRepository == null){
            RateDataMapper dataMapper = null;
            try {
                dataMapper = new RateDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            rateRepository = new RateRepository(dataMapper);
        }
        if(paymentRepository == null){
            PaymentDataMapper dataMapper = null;
            try {
                dataMapper = new PaymentDataMapper();
            }
            catch (SQLException e){
                throw new ApplicationError(e);
            }
            paymentRepository = new PaymentRepository(dataMapper);
        }

        passengerFacade = PassengerFacade.initInstance(passengerRepository, orderRepository, addressRepository);
        driverFacade = DriverFacade.initInstance(driverRepository, orderRepository, rateRepository, paymentRepository);
        operatorFacade = OperatorFacade.initInstance(operatorRepository, driverRepository, orderRepository);
    }

    public static ApplicationFacade getInstance() throws ApplicationError {
        if (instance == null) {
            instance = new ApplicationFacade();
        }
        return instance;
    }
}
