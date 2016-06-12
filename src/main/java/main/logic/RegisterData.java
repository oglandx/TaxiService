package main.logic;

import java.sql.Date;

/**
 * Created by oglandx on 4/30/16.
 */
public class RegisterData extends AuthData {
    private String lastName;
    private String firstName;
    private String middleName;
    private Date birthDate;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public RegisterData(final String lastName, final String firstName, final String middleName, final Date birthDate,
                        final String email, final String passHash){
        super(email, passHash);
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
    }
}
