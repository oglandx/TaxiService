package taxi.logic;

import java.util.Objects;

/**
 * Created by oglandx on 4/30/16.
 */
public class AuthData {
    public final String email;
    public final String passHash;

    public AuthData(final String email, final String passHash){
        this.email = email;
        this.passHash = passHash;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AuthData &&
                this.email.equals(((AuthData) o).email) &&
                this.passHash.equals(((AuthData) o).passHash);
    }
}
