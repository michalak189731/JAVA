package parking.exceptions;

/**
 * Created by emazmik on 2017-06-14.
 */
public class CurrentlyRentingException  extends Exception {
    public CurrentlyRentingException(String message) {
        super(message);
    }
}
