/**
 * Thrown when an operation refers to an accommodation ID that does not
 * exist in the system (e.g. trying to book, update, or delete it).
 */
public class AccommodationNotFoundException extends Exception {
    public AccommodationNotFoundException(String accommodationId) {
        super("No accommodation found with ID: " + accommodationId);
    }
}
