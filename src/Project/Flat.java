package Project;

/**
 * Represents a flat in a real estate system, with methods to access the flat type, 
 * the total number of units, the selling price, and the number of booked or available units.
 * Also provides functionality for booking a unit and converting the flat type to a string.
 */
public interface Flat {

    /**
     * Enum representing the possible types of flats.
     */
    public enum Type {
        TWOROOM, // Represents a flat with 2 rooms
        THREEROOM; // Represents a flat with 3 rooms
    }

    /**
     * Gets the type of the flat (either TWOROOM or THREEROOM).
     * @return The type of the flat.
     */
    Type getFlatType();

    /**
     * Gets the total number of units available for this flat.
     * @return The total number of units.
     */
    int getTotalUnits();

    /**
     * Gets the selling price of a single unit of the flat.
     * @return The selling price of one unit.
     */
    int getSellingPrice();

    /**
     * Gets the number of units that have already been booked.
     * @return The number of booked units.
     */
    int getUnitsBooked();

    /**
     * Gets the number of available units for the flat.
     * @return The number of available units.
     */
    int getAvailableUnits();

    /**
     * Books a unit of the flat. Returns true if the booking was successful, 
     * and false if no units are available.
     * @return true if the unit was successfully booked, otherwise false.
     */
    boolean bookUnit();

    /**
     * Converts the flat type to a string representation (either "2" or "3").
     * @return The string representation of the flat type.
     */
    default String typeToString() {
        String roomCount = (getFlatType() == Type.TWOROOM) ? "2" : "3";
        return roomCount;
    }

    /**
     * Converts the given flat type to a string representation (either "2-Room" or "3-Room").
     * @param flatType The type of flat (either TWOROOM or THREEROOM).
     * @return The string representation of the flat type.
     */
    default String typeToString(Flat.Type flatType) {
        int roomCount = (flatType == Type.TWOROOM) ? 2 : 3;
        return (roomCount + "-Room");
    }
}
