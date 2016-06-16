package main.repository;

import main.common.Query;
import main.database.PassengerDataMapper;
import main.logic.Passenger;
import main.logic.RegisterData;
import main.repository.exceptions.MultipleObjectsException;
import main.repository.exceptions.ObjectNotFoundException;

import java.sql.Date;

/**
 * Created by oglandx on 6/16/16.
 */
public class RepositoryTestDatabase {
//    private Repository<Passenger> passengerRepository;
//    private String uniqueLastName = "wfgqufiuewhgfwhe127484g2fhh83tw39h2";
//
//    @org.junit.Before
//    public void setUp() throws Exception {
//        PassengerDataMapper dataMapper = new PassengerDataMapper();
//        passengerRepository = new PassengerRepository(dataMapper);
//    }
//
//    @org.junit.After
//    public void tearDown() throws Exception {
//        passengerRepository.closeDataMapperConnection();
//    }
//
//    @org.junit.Test
//    public void getDataMapper() throws Exception {
//        assert passengerRepository.getDataMapper() != null;
//    }
//
//    @org.junit.Test
//    public void all() throws Exception {
//        assert passengerRepository.all() != null;
//    }
//
////    @org.junit.Test (expected = ObjectNotFoundException.class)
////    public void getTestEmpty() throws Exception {
////        Query query = new Query("{'lastname': '" + uniqueLastName + "'}");
////        passengerRepository.get(query);
////    }
//
//    @org.junit.Test
//    public void getTestOne() throws Exception {
//        Query query = new Query("{'lastname': '" + uniqueLastName + "'}");
//        passengerRepository.create(new Passenger(new RegisterData(uniqueLastName, "", "", new Date(0), "", "")));
//        assert passengerRepository.get(query) != null;
//        assert passengerRepository.remove(query);
//    }
//
//    @org.junit.Test (expected = MultipleObjectsException.class)
//    public void getTestMany() throws Exception {
//        Query query = new Query("{'lastname': '" + uniqueLastName + "'}");
//        passengerRepository.create(new Passenger(new RegisterData(uniqueLastName, "", "", new Date(0), "", "")));
//        passengerRepository.create(new Passenger(new RegisterData(uniqueLastName, "", "", new Date(0), "", "")));
//        passengerRepository.get(query);
//        assert passengerRepository.remove(query);
//    }
//
//    @org.junit.Test
//    public void create() throws Exception {
//        passengerRepository.create(new Passenger(new RegisterData("", "", "", new Date(0), "", "")));
//    }
//
////    @org.junit.Test
////    public void filter() throws Exception {
////        Query query = new Query("{'lastname': '" + uniqueLastName + "'}");
////        passengerRepository.create(new Passenger(new RegisterData(uniqueLastName, "", "", new Date(0), "", "")));
////        passengerRepository.create(new Passenger(new RegisterData(uniqueLastName, "", "", new Date(0), "", "")));
////        assert passengerRepository.filter(query).size() == 2;
////        assert passengerRepository.remove(query);
////    }

}