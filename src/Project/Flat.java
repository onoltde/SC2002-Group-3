package Project;

public interface Flat {

        public enum Type {
            TWOROOM,
            THREEROOM;
        }

        Type getFlatType();

        int getTotalUnits();

        int getSellingPrice();

        int getUnitsBooked();

        int getAvailableUnits();

        default String typeToString(){
            String roomCount = (getFlatType() == Type.TWOROOM) ? "2" : "3";
            return (roomCount);
        }

        default String typeToString(Flat.Type flatType) {
            int roomCount = (flatType == Type.TWOROOM) ? 2 : 3;
            return (roomCount+ "-Room");
        }

}//end of class