package main.repository;

import main.common.Query;
import main.logic.Passenger;
import main.logic.RegisterData;
import main.repository.exceptions.MultipleObjectsException;
import main.repository.exceptions.ObjectNotFoundException;

import java.sql.Date;

import static org.junit.Assert.*;

/**
 * Created by oglandx on 6/16/16.
 */
public class RepositoryTest {
    private Repository<Passenger> passengerRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        passengerRepository = new PassengerRepository();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        passengerRepository.closeDataMapperConnection();
    }

    @org.junit.Test
    public void getDataMapper() throws Exception {
        assert passengerRepository.getDataMapper() == null;
    }

    @org.junit.Test
    public void all() throws Exception {
        assert passengerRepository.all() != null;
    }

    @org.junit.Test (expected = ObjectNotFoundException.class)
    public void getTestEmpty() throws Exception {
        Query query = new Query("{'id': '" + 0 + "'}");
        passengerRepository.get(query);
    }

    @org.junit.Test
    public void getTestOne() throws Exception {
        Query query = new Query("{'id': '" + 0 + "'}");
        passengerRepository.create(new Passenger(new RegisterData("", "", "", new Date(0), "", "")));
        assert passengerRepository.get(query) != null;
    }

    @org.junit.Test (expected = MultipleObjectsException.class)
    public void getTestMany() throws Exception {
        Query query = new Query("{'id': '" + 0 + "'}");
        passengerRepository.create(new Passenger(new RegisterData("", "", "", new Date(0), "", "")));
        passengerRepository.create(new Passenger(new RegisterData("", "", "", new Date(0), "", "")));
        passengerRepository.get(query);
    }

    @org.junit.Test
    public void create() throws Exception {
        passengerRepository.create(new Passenger(new RegisterData("", "", "", new Date(0), "", "")));
    }

    @org.junit.Test
    public void remove() throws Exception {
        Query query = new Query("{'id': '" + 0 + "'}");
        assert !passengerRepository.remove(query);
    }

    @org.junit.Test
    public void remove1() throws Exception {
        Query query = new Query("{'id': '" + 0 + "'}");
        passengerRepository.create(new Passenger(new RegisterData("", "", "", new Date(0), "", "")));
        assert passengerRepository.remove(query);
    }

    @org.junit.Test
    public void filter() throws Exception {
        Query query = new Query("{'lastname': 'last'}");
        passengerRepository.create(new Passenger(new RegisterData("last", "", "", new Date(0), "", "")));
        assert passengerRepository.remove(query);
    }

}