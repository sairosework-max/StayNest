import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Represents a single booking made by a guest for a specific accommodation.
 * ENCAPSULATION: fields are private with validated setters.
 */
public class Booking {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private String bookingId;
    private String userId;
    private String guestName;
    private String accommodationId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int numberOfGuests;
    private double totalPrice;
    private BookingStatus status;

    public Booking(String bookingId, String userId, String guestName, String accommodationId,
                    LocalDate checkIn, LocalDate checkOut, int numberOfGuests,
                    double totalPrice, BookingStatus status) {
        this.bookingId = bookingId;
        this.userId = userId;
        setGuestName(guestName);
        this.accommodationId = accommodationId;
        setDates(checkIn, checkOut);
        setNumberOfGuests(numberOfGuests);
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserId() { return userId; }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidInputException("Guest name cannot be empty.");
        }
        this.guestName = guestName;
    }

    public String getAccommodationId() {
        return accommodationId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            throw new InvalidInputException("Check-out date must be after check-in date.");
        }
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getNumberOfNights() {
        return (int) ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests <= 0) {
            throw new InvalidInputException("Number of guests must be at least 1.");
        }
        this.numberOfGuests = numberOfGuests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    /** Serialises this booking to the pipe-delimited text file format. */
    public String toFileString() {
        return String.join("|", bookingId, userId, guestName, accommodationId,
                checkIn.format(DATE_FORMAT), checkOut.format(DATE_FORMAT),
                String.valueOf(numberOfGuests), String.valueOf(totalPrice), status.name());
    }

    /** Parses one line of the bookings text file back into a Booking object. */
    public static Booking fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 9) {
            throw new InvalidInputException("Malformed booking record: " + line);
        }
        try {
            String id = parts[0];
            String userId = parts[1];
            String guest = parts[2];
            String accommodationId = parts[3];
            LocalDate checkIn = LocalDate.parse(parts[4], DATE_FORMAT);
            LocalDate checkOut = LocalDate.parse(parts[5], DATE_FORMAT);
            int guests = Integer.parseInt(parts[6]);
            double price = Double.parseDouble(parts[7]);
            BookingStatus status = BookingStatus.fromString(parts[8]);
            return new Booking(id, userId, guest, accommodationId, checkIn, checkOut, guests, price, status);
        } catch (Exception e) {
            throw new InvalidInputException("Could not parse booking record: " + line);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] Guest: %-15s | Accommodation: %-6s | %s -> %s (%d nights) | Guests: %-2d | Total: $%.2f | %s",
                bookingId, guestName, accommodationId, checkIn, checkOut,
                getNumberOfNights(), numberOfGuests, totalPrice, status);
    }
}
