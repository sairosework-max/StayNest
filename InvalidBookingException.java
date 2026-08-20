/**
 * Thrown when a booking request breaks a business rule, e.g. check-out
 * before check-in, zero/negative nights, too many guests for the
 * accommodation, or booking an unavailable property.
 */
public class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}
