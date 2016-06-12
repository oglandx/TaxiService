package main.logic;

/**
 * Created by oglandx on 4/30/16.
 */
public class AuthData {
    private String email;
    private String passHash;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

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
