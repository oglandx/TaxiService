package main.logic;

/**
 * Created by oglandx on 5/8/16.
 */
public final class Address extends Entity {
    private String city;
    private String street;
    private String building;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Address(final String city, final String street, final String building){
        setCity(city);
        setStreet(street);
        setBuilding(building);
    }

    public Address(int id, final String city, final String street, final String building){
        setId(id);
        setCity(city);
        setStreet(street);
        setBuilding(building);
    }
}
