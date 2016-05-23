package taxi.logic;

/**
 * Created by oglandx on 5/8/16.
 */
public final class Address {
    public final String city;
    public final String street;
    public final String building;

    public Address(final String city, final String street, final String building){
        this.city = city;
        this.street = street;
        this.building = building;
    }
}
