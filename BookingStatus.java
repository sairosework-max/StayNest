/**
 * Represents the lifecycle status of a Booking.
 * Using an enum keeps the set of valid statuses fixed and type-safe,
 * instead of relying on error-prone raw Strings.
 */
public enum BookingStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    COMPLETED;

    /**
     * Safely converts a String (e.g. read from a file) back into a BookingStatus.
     * Falls back to PENDING if the text is unrecognised, rather than crashing.
     */
    public static BookingStatus fromString(String text) {
        try {
            return BookingStatus.valueOf(text.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return BookingStatus.PENDING;
        }
    }
}
