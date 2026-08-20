/**
 * House: a standalone property, optionally with a garden and multiple floors.
 */
public class House extends Accommodation {

    private boolean hasGarden;
    private int numberOfFloors;
    private static final double GARDEN_MAINTENANCE_FEE = 20.0;

    public House(String id, String name, String location, double pricePerNight,
                 int maxGuests, boolean available, boolean hasGarden, int numberOfFloors) {
        super(id, name, location, pricePerNight, maxGuests, available);
        this.hasGarden = hasGarden;
        setNumberOfFloors(numberOfFloors);
    }

    public boolean hasGarden() {
        return hasGarden;
    }

    public void setHasGarden(boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        if (numberOfFloors <= 0) {
            throw new InvalidInputException("Number of floors must be at least 1.");
        }
        this.numberOfFloors = numberOfFloors;
    }

    // POLYMORPHISM: House's own pricing rule = nightly rate + optional garden fee
    @Override
    public double calculateTotalPrice(int nights) {
        if (nights <= 0) {
            throw new InvalidInputException("Number of nights must be positive.");
        }
        double total = getPricePerNight() * nights;
        if (hasGarden) {
            total += GARDEN_MAINTENANCE_FEE;
        }
        return total;
    }

    @Override
    public String getCategory() {
        return "House";
    }

    @Override
    public String getExtraDetails() {
        return numberOfFloors + " floor(s), " + (hasGarden ? "has garden" : "no garden");
    }

    @Override
    public String toFileString() {
        return String.join("|", "HOUSE", getId(), getName(), getLocation(),
                String.valueOf(getPricePerNight()), String.valueOf(getMaxGuests()),
                String.valueOf(isAvailable()), String.valueOf(hasGarden), String.valueOf(numberOfFloors));
    }
}
