import java.util.*;
import java.time.*;

public class Project {

    private final String name;
    private final String neighbourhood;
    private HashMap<Flat.Type,Flat> flatInfo;
    private final LocalDate openDate;
    private final LocalDate closeDate;
    private String managerId;
    private int officerSlots;
    private ArrayList<String> assignedOfficers;
    private boolean visibility;
    private ArrayList<String> pendingOfficers;

    public Project(String name, String neighbourhood, HashMap<Flat.Type,Flat> flatInfo, LocalDate openDate,
                   LocalDate closeDate, String managerId, int officerSlots,
                   ArrayList<String> assignedOfficers, boolean visibility, ArrayList<String> pendingOfficers) {
        this.name = name;
        this.neighbourhood = neighbourhood;
        this.flatInfo = flatInfo;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.managerId = managerId;
        this.officerSlots = officerSlots;
        this.assignedOfficers = assignedOfficers;
        this.visibility = visibility;
        this.pendingOfficers = pendingOfficers;
    }

    // Applicant NRIC → Applicant
    private Map<String, Applicant> applicants = new HashMap<>();

//    // Flat booking methods
//    public boolean hasFlatAvailable(String flatType) {
//        for (Flat flat : flatInfo) {
//            if (flat.getFlatType() != null && flat.getFlatType().equalsIgnoreCase(flatType) && flat.getAvailableUnits() > 0) {
//                return true;
//            }
//        }
//        return false;
//    }

//    public boolean decreaseFlatCount(String flatType) {
//        for (Flat flat : flatInfo) {
//            if (flat.getFlatType() != null && flat.getFlatType().equalsIgnoreCase(flatType)) {
//                if (flat.getAvailableUnits() > 0) {
//                    flat.setAvailableUnits(flat.getAvailableUnits() - 1);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", flatInfo=" + flatInfo +
                ", openDate=" + openDate +
                ", closeDate=" + closeDate +
                ", managerId='" + managerId + '\'' +
                ", officerSlots=" + officerSlots +
                ", assignedOfficers=" + assignedOfficers +
                ", visibility=" + visibility +
                ", pendingOfficers=" + pendingOfficers +
                '}';
    }

    // Officer management
    public void addPendingOfficer(String officerId) {
        pendingOfficers.add(officerId);
    }

    public void removePendingOfficer(String officerId) {
        pendingOfficers.remove(officerId);
    }

    public void addAssignedOfficer(String officerId) {
        if (assignedOfficers.size() < officerSlots) {
            assignedOfficers.add(officerId);
        }
    }

    public void removeAssignedOfficer(String officerId) {
        assignedOfficers.remove(officerId);
    }

    //for filters
    public boolean hasAvailUnits(Flat.Type roomType){
        Flat flat= this.getFlatInfo().get(roomType);
        return flat != null && flat.getAvailableUnits() > 0;
    }

    public boolean isWithinDateRange() {
        LocalDate currentDate = LocalDate.now();
        return !currentDate.isBefore(openDate) && !currentDate.isAfter(closeDate);
    }

    public void displayProjectDetails(){
        int totalUnits2 = this.getFlatInfo().get(Flat.Type.TWOROOM).getTotalUnits();
        int availUnits2 = this.getFlatInfo().get(Flat.Type.TWOROOM).getAvailableUnits();
        int price2 = this.getFlatInfo().get(Flat.Type.TWOROOM).getSellingPrice();

        int totalUnits3 = this.getFlatInfo().get(Flat.Type.THREEROOM).getTotalUnits();
        int availUnits3 = this.getFlatInfo().get(Flat.Type.THREEROOM).getAvailableUnits();
        int price3 = this.getFlatInfo().get(Flat.Type.THREEROOM).getSellingPrice();

        LocalDate openDate = this.getOpenDate();
        LocalDate closeDate = this.getCloseDate();

        System.out.println("Project Name: " + getName());
        System.out.println("Neigbourhood: " + getNeighbourhood());
        System.out.println("Flat Type: " + Flat.Type.TWOROOM);
        System.out.println("Number of Available units2: " + availUnits2 + "/" + totalUnits2);
        System.out.println("Price of each unit2: $" + price2);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println("Flat Type: " + Flat.Type.THREEROOM);
        System.out.println("Number of Available units3: " + availUnits3 + "/" + totalUnits3);
        System.out.println("Price of each unit3: $" + price3);

        System.out.println("Application open date: " + openDate);
        System.out.println("Application close date: " + closeDate);
        System.out.println("------------------------------------------------------------");

    }


    // Getters
    public String getName() {
        return name;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public HashMap<Flat.Type,Flat> getFlatInfo() {
        return flatInfo;
    }

    public String getManagerId() {
        return managerId;
    }

    public int getOfficerSlots() {
        return officerSlots;
    }

    public void setOfficerSlots(int officerSlots) {
        this.officerSlots = officerSlots;
    }

    public ArrayList<String> getAssignedOfficers() {
        return new ArrayList<>(assignedOfficers);
    }

    public boolean isVisible() {
        return visibility;
    }

    public ArrayList<String> getPendingOfficers() {
        return new ArrayList<>(pendingOfficers);
    }
}
