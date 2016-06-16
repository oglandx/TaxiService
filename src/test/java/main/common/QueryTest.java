package main.common;

import main.logic.Address;
import main.logic.Order;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oglandx on 6/16/16.
 */
public class QueryTest {
    @Test
    public void check() throws Exception {
        assert new Query("{'id': '0'}").check(new Address("", "", ""));
    }

    @Test
    public void checkOr() throws Exception {
        Query query = new Query("{'OR': {'id': '100500', 'city': 'NY'}}");
        assert query.check(new Address("NY", "", ""));
    }

    @Test
    public void checkGt() throws Exception {
        assert !(new Query("{'id__gt': '10'}").check(new Address("", "", "")));
    }

    @Test
    public void sql() throws Exception {
        assert new Query("{'OR': {'id': '100500', 'city': 'NY'}}").sql().equals("((city = 'NY' OR id = '100500'))");
    }

}