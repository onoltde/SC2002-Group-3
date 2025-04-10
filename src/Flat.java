public class Flat {

        public enum Type {
            TWOROOM,
            THREEROOM,
            NONE;

        }

        private final Type flatType;
        private int totalUnits;
        private int sellingPrice;
        private int unitsBooked;

        public Flat(Type flatType, int totalUnits, int numOfBookedUnits, int sellingPrice){
            this.flatType = flatType;
            this.totalUnits = totalUnits;
            this.sellingPrice = sellingPrice;
            this.unitsBooked = numOfBookedUnits;
        }

        public Type getFlatType() {
            return flatType;
        }

        public int getTotalUnits() {
            return totalUnits;
        }

        public int getSellingPrice() {
            return sellingPrice;
        }

        public int getUnitsBooked() {
            return unitsBooked;
        }

        public int getAvailableUnits() { return totalUnits-unitsBooked; }


    }



