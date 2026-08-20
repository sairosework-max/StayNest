/**
 * Apartment: a compact, self-contained accommodation.
 * INHERITANCE: extends Accommodation and reuses its common fields/behaviour.
 */
public class Apartment extends Accommodation {

    private int numberOfBedrooms;
    private static final double CLEANING_FEE = 30.0;

    public Apartment(String id, String name, String location, double pricePerNight,
                      int maxGuests, boolean available, int numberOfBedrooms) {
        super(id, name, location, pricePerNight, maxGuests, available);
        setNumberOfBedrooms(numberOfBedrooms);
    }

    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(int numberOfBedrooms) {
        if (numberOfBedrooms <= 0) {
            throw new InvalidInputException("Number of bedrooms must be at least 1.");
        }
        this.numberOfBedrooms = numberOfBedrooms;
    }

    // POLYMORPHISM: Apartment's own pricing rule = nightly rate + flat cleaning fee
    @Override
    public double calculateTotalPrice(int nights) {
        if (nights <= 0) {
            throw new InvalidInputException("Number of nights must be positive.");
        }
        return getPricePerNight() * nights + CLEANING_FEE;
    }

    @Override
    public String getCategory() {
        return "Apartment";
    }

    @Override
    public String getExtraDetails() {
        return numberOfBedrooms + " bedroom(s)";
    }

    @Override
    public String toFileString() {
        return String.join("|", "APARTMENT", getId(), getName(), getLocation(),
                String.valueOf(getPricePerNight()), String.valueOf(getMaxGuests()),
                String.valueOf(isAvailable()), String.valueOf(numberOfBedrooms));
    }
}
