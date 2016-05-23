package taxi.logic;

/**
 * Created by oglandx on 5/15/16.
 */
public enum OrderStatus {
    /* Just created by passenger */
    /* Can go to: PROCESSING, DECLINED */
    NEW,

    /* Processing by operator: choosing a driver */
    /* Can go to: ACCEPTED, DECLINED */
    PROCESSING,

    /* No available drivers */
    /* Can go to: DEAD */
    DECLINED,

    /* Accepted by driver */
    /* Can go to: EXECUTED, DECLINED */
    ACCEPTED,

    /* Order is executed */
    /* Can go to: <NULL> */
    EXECUTED,

    /* Order is dead */
    /* Can go to: <NULL> */
    DEAD;

    static boolean isAvailable(final OrderStatus previousState, final OrderStatus nextState){
        boolean result = false;
        switch (previousState){
            case NEW:
                result = nextState == PROCESSING || nextState == DECLINED;
                break;
            case PROCESSING:
                result = nextState == ACCEPTED || nextState == DECLINED;
                break;
            case DECLINED:
                result = nextState == DEAD;
                break;
            case ACCEPTED:
                result = nextState == EXECUTED || nextState == DECLINED;
                break;
            case EXECUTED:
                break;
        }
        return result;
    }
}
