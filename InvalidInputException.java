/**
 * Thrown when raw input (from the console or a data file) cannot be
 * parsed into a valid value, e.g. a non-numeric price or a malformed
 * date/line in a text file.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
