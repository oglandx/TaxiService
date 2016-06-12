package main.logic;

/**
 * Created by oglandx on 5/15/16.
 */
public enum DriverStatus {
    FREE ("FREE"),
    READY ("READY"),
    BUSY ("BUSY");

    private final String id;

    DriverStatus(final String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    static boolean isAvailable(final DriverStatus previousState, final DriverStatus nextState){
        boolean result = false;
        switch (previousState){
            case FREE:
                result = nextState == BUSY;
                break;
            case BUSY:
                result = nextState == FREE;
                break;
        }
        return result;
    }
}
