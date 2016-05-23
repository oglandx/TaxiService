package taxi.logic;

import java.util.Date;

/**
 * Created by oglandx on 4/30/16.
 */
final class RegisterData extends AuthData {
    public final String lastName;
    public final String firstName;
    public final String middleName;
    public final Date birthDate;

    public RegisterData(final String lastName, final String firstName, final String middleName, final Date birthDate,
                        final String email, final String passHash){
        super(email, passHash);
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
    }
}
