/**
 * Abstract base class representing a bookable property in the system.
 *
 * ABSTRACTION: Accommodation defines the common contract (fields + behaviour)
 * shared by every property type, while leaving type-specific pricing and
 * description logic to be implemented by subclasses.
 *
 * ENCAPSULATION: all fields are private; access is only through validated
 * getters/setters, so an Accommodation can never be put into an invalid
 * state (e.g. negative price) from outside the class.
 */
public abstract class Accommodation {

    private String id;
    private String name;
    private String location;
    private double pricePerNight;
    private int maxGuests;
    private boolean available;

    public Accommodation(String id, String name, String location,
                          double pricePerNight, int maxGuests, boolean available) {
        this.id = id;
        this.name = name;
        this.location = location;
        setPricePerNight(pricePerNight);
        setMaxGuests(maxGuests);
        this.available = available;
    }

    // ---------- Abstract methods: implemented differently by each subclass (POLYMORPHISM) ----------

    /** Calculates the total price for a stay of the given number of nights. */
    public abstract double calculateTotalPrice(int nights);

    /** Returns a human-readable category name, e.g. "Apartment". */
    public abstract String getCategory();

    /** Returns a short description of the type-specific extra features. */
    public abstract String getExtraDetails();

    /** Serialises the type-specific fields into the pipe-delimited file format. */
    public abstract String toFileString();

    // ---------- Encapsulated getters / setters with basic validation ----------

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty.");
        }
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new InvalidInputException("Location cannot be empty.");
        }
        this.location = location;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        if (pricePerNight < 0) {
            throw new InvalidInputException("Price per night cannot be negative.");
        }
        this.pricePerNight = pricePerNight;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        if (maxGuests <= 0) {
            throw new InvalidInputException("Max guests must be at least 1.");
        }
        this.maxGuests = maxGuests;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] %-6s | %-20s | %-15s | $%8.2f/night | Max guests: %-2d | %-11s | %s",
                id, getCategory(), name, location, pricePerNight, maxGuests,
                (available ? "Available" : "Unavailable"), getExtraDetails());
    }
}
