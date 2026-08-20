/**
 * Villa: a premium property that may include a pool and dedicated staff.
 */
public class Villa extends Accommodation {

    private boolean hasPool;
    private boolean staffIncluded;
    private static final double POOL_FEE = 50.0;
    private static final double STAFF_FEE_PER_NIGHT = 40.0;

    public Villa(String id, String name, String location, double pricePerNight,
                 int maxGuests, boolean available, boolean hasPool, boolean staffIncluded) {
        super(id, name, location, pricePerNight, maxGuests, available);
        this.hasPool = hasPool;
        this.staffIncluded = staffIncluded;
    }

    public boolean hasPool() {
        return hasPool;
    }

    public void setHasPool(boolean hasPool) {
        this.hasPool = hasPool;
    }

    public boolean isStaffIncluded() {
        return staffIncluded;
    }

    public void setStaffIncluded(boolean staffIncluded) {
        this.staffIncluded = staffIncluded;
    }

    // POLYMORPHISM: Villa's own pricing rule = nightly rate + pool fee + per-night staff fee
    @Override
    public double calculateTotalPrice(int nights) {
        if (nights <= 0) {
            throw new InvalidInputException("Number of nights must be positive.");
        }
        double total = getPricePerNight() * nights;
        if (hasPool) {
            total += POOL_FEE;
        }
        if (staffIncluded) {
            total += STAFF_FEE_PER_NIGHT * nights;
        }
        return total;
    }

    @Override
    public String getCategory() {
        return "Villa";
    }

    @Override
    public String getExtraDetails() {
        return (hasPool ? "pool, " : "no pool, ") + (staffIncluded ? "staff included" : "no staff");
    }

    @Override
    public String toFileString() {
        return String.join("|", "VILLA", getId(), getName(), getLocation(),
                String.valueOf(getPricePerNight()), String.valueOf(getMaxGuests()),
                String.valueOf(isAvailable()), String.valueOf(hasPool), String.valueOf(staffIncluded));
    }
}
