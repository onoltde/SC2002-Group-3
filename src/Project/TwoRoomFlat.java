package Project;

public class TwoRoomFlat implements Flat {

    private Flat.Type flatType;
    private int totalUnits;
    private int sellingPrice;
    private int unitsBooked;

    /**
     * Constructs a TwoRoomFlat with the specified details.
     *
     * @param flatType the type of the flat
     * @param totalUnits the total number of units available for the flat
     * @param numOfBookedUnits the number of units that have already been booked
     * @param sellingPrice the selling price of the flat
     */
    public TwoRoomFlat(Flat.Type flatType, int totalUnits, int numOfBookedUnits, int sellingPrice) {
        this.flatType = flatType;
        this.totalUnits = totalUnits;
        this.sellingPrice = sellingPrice;
        this.unitsBooked = numOfBookedUnits;
    }

    /**
     * Returns the total number of units available for this flat.
     *
     * @return the total number of units
     */
    public int getTotalUnits() {
        return totalUnits;
    }

    /**
     * Returns the selling price of this flat.
     *
     * @return the selling price
     */
    public int getSellingPrice() {
        return sellingPrice;
    }

    /**
     * Returns the number of units that have already been booked.
     *
     * @return the number of booked units
     */
    public int getUnitsBooked() {
        return unitsBooked;
    }

    /**
     * Returns the number of available units for this flat.
     *
     * @return the number of available units
     */
    public int getAvailableUnits() {
        return totalUnits - unitsBooked;
    }

    /**
     * Returns the type of this flat.
     *
     * @return the flat type
     */
    public Type getFlatType(){
        return flatType;
    }

    /**
     * Books a unit for this flat. If there are no available units, it returns false.
     * Otherwise, it increases the number of booked units and returns true.
     *
     * @return true if the unit was successfully booked, false if no units are available
     */
    public boolean bookUnit() {
        if (getAvailableUnits() < 1) {
            return false;
        } else {
            this.unitsBooked += 1;
            return true;
        }
    }

}
