package main.logic;

/**
 * Created by oglandx on 5/15/16.
 */
public enum OrderStatus {
    /* Just created by passenger */
    /* Can go to: PROCESSING, DECLINED */
    NEW ("NEW"),

    /* Processing by operator: choosing a driver */
    /* Can go to: ACCEPTED, DECLINED */
    PROCESSING ("PROCESSING"),

    /* No available drivers */
    /* Can go to: DEAD */
    DECLINED ("DECLINED"),

    /* Accepted by driver */
    /* Can go to: EXECUTED, DECLINED */
    ACCEPTED ("ACCEPTED"),

    /* Order is executed */
    /* Can go to: <NULL> */
    EXECUTED ("EXECUTED"),

    /* Order is dead */
    /* Can go to: <NULL> */
    DEAD ("DEAD");

    private final String id;

    OrderStatus(final String id){
        this.id = id != null ? id : "NEW";
    }

    public String getId() {
        return id;
    }

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

    public boolean eq(OrderStatus orderStatus){
        return this.getId().equals(orderStatus.getId());
    }
}
