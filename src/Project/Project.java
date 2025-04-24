package Project;
import java.util.*;
import java.time.*;

/**
 * Represents a project in a real estate system, which includes details about flats,
 * officer assignments, and various filters such as availability, visibility, and date ranges.
 */
public class Project {

    private final String name;
    private final String neighbourhood;
    private HashMap<Flat.Type, Flat> flatInfo;
    private final LocalDate openDate;
    private final LocalDate closeDate;
    private String managerId;
    private int officerSlots;
    private ArrayList<String> assignedOfficers;
    private boolean visibility;
    private ArrayList<String> pendingOfficers;

    /**
     * Constructs a new Project object.
     * @param name The name of the project.
     * @param neighbourhood The neighbourhood where the project is located.
     * @param flatInfo The map of available flats in the project, categorized by type.
     * @param openDate The opening date of the project.
     * @param closeDate The closing date of the project.
     * @param managerId The ID of the project manager.
     * @param officerSlots The number of officer slots available for the project.
     * @param assignedOfficers A list of assigned officers for the project.
     * @param visibility The visibility status of the project (whether it's public or private).
     * @param pendingOfficers A list of officers who are pending assignment.
     */
    public Project(String name, String neighbourhood, HashMap<Flat.Type, Flat> flatInfo, LocalDate openDate,
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

    // Officer management methods
    /**
     * Adds an officer to the list of pending officers.
     * @param officerId The ID of the officer to add.
     */
    public void addPendingOfficer(String officerId) {
        pendingOfficers.add(officerId);
    }

    /**
     * Removes an officer from the list of pending officers.
     * @param officerId The ID of the officer to remove.
     */
    public void removePendingOfficer(String officerId) {
        pendingOfficers.remove(officerId);
    }

    /**
     * Adds an officer to the list of assigned officers if there is space.
     * @param officerId The ID of the officer to assign.
     */
    public void addAssignedOfficer(String officerId) {
        if (assignedOfficers.size() < officerSlots) {
            assignedOfficers.add(officerId);
        }
    }

    /**
     * Removes an officer from the list of assigned officers.
     * @param officerId The ID of the officer to remove.
     */
    public void removeAssignedOfficer(String officerId) {
        assignedOfficers.remove(officerId);
    }

    // Filter methods
    /**
     * Checks if there are available units of the specified flat type.
     * @param roomType The type of flat (e.g., TWO_ROOM, THREE_ROOM).
     * @return true if there are available units, false otherwise.
     */
    public boolean hasAvailUnits(Flat.Type roomType) {
        Flat flat = this.getFlatInfo().get(roomType);
        return flat != null && flat.getAvailableUnits() > 0;
    }

    /**
     * Checks if the project is currently within the open and close date range.
     * @return true if the current date is within the project date range, false otherwise.
     */
    public boolean isWithinDateRange() {
        LocalDate currentDate = LocalDate.now();
        return !currentDate.isBefore(openDate) && !currentDate.isAfter(closeDate);
    }

    /**
     * Checks if there is an overlap between this project and another project in terms of dates.
     * @param otherProject The other project to check for date overlap.
     * @return true if there is an overlap, false otherwise.
     */
    public boolean dateOverlap(Project otherProject) {
        return !(this.getCloseDate().isBefore(otherProject.getOpenDate()) &&
                !(otherProject.getCloseDate().isBefore(this.getOpenDate())));
    }

    // Getters and Setters
    /**
     * Gets the name of the project.
     * @return The name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the neighbourhood where the project is located.
     * @return The neighbourhood of the project.
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Gets the opening date of the project.
     * @return The opening date.
     */
    public LocalDate getOpenDate() {
        return openDate;
    }

    /**
     * Gets the closing date of the project.
     * @return The closing date.
     */
    public LocalDate getCloseDate() {
        return closeDate;
    }

    /**
     * Gets the flat information, categorized by flat type.
     * @return A map of flat types to their respective flat objects.
     */
    public HashMap<Flat.Type, Flat> getFlatInfo() {
        return flatInfo;
    }

    /**
     * Gets the ID of the project manager.
     * @return The manager's ID.
     */
    public String getManagerId() {
        return managerId;
    }

    /**
     * Gets the number of officer slots available for the project.
     * @return The number of officer slots.
     */
    public int getOfficerSlots() {
        return officerSlots;
    }

    /**
     * Sets the number of officer slots available for the project.
     * @param officerSlots The new number of officer slots.
     */
    public void setOfficerSlots(int officerSlots) {
        this.officerSlots = officerSlots;
    }

    /**
     * Gets the list of assigned officers for the project.
     * @return A list of officer IDs assigned to the project.
     */
    public ArrayList<String> getAssignedOfficers() {
        return new ArrayList<>(assignedOfficers);
    }

    /**
     * Checks if the project is visible (public).
     * @return true if the project is visible, false if it is private.
     */
    public boolean isVisible() {
        return visibility;
    }

    /**
     * Toggles the visibility status of the project.
     */
    public void toggleVisibility() {
        visibility = !visibility;
    }

    /**
     * Gets the list of pending officers for the project.
     * @return A list of officer IDs pending assignment.
     */
    public ArrayList<String> getPendingOfficers() {
        return new ArrayList<>(pendingOfficers);
    }
}
