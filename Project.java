package Project;

import java.util.List;
import java.util.Map;

enum ApplicationStatus {
    PENDING,
    SUCCESSFUL,
    UNSUCCESSFUL,
    BOOKED
}

enum RegistrationStatus {
    PENDING,
    APPROVED,
    REJECTED
}

class Project {
    private String name;
    private String neighborhood;
    private List<String> flatTypes;
    private Map<String, Integer> unitsAvailable;
    private String openingDate;
    private String closingDate;
    private boolean visibility;
    private HDBManager manager;
    private List<HDBOfficer> officers;

    public Project(String name, String neighborhood, List<String> flatTypes, Map<String, Integer> unitsAvailable,
                   String openingDate, String closingDate, boolean visibility, HDBManager manager, List<HDBOfficer> officers) {
        this.name = name;
        this.neighborhood = neighborhood;
        this.flatTypes = flatTypes;
        this.unitsAvailable = unitsAvailable;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.visibility = visibility;
        this.manager = manager;
        this.officers = officers;
    }

    public String getName() {
        return name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public List<String> getFlatTypes() {
        return flatTypes;
    }

    public Map<String, Integer> getUnitsAvailable() {
        return unitsAvailable;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public HDBManager getManager() {
        return manager;
    }

    public List<HDBOfficer> getOfficers() {
        return officers;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void setUnitsAvailable(Map<String, Integer> unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }
    public void toggleVisibility() {
        this.visibility = !this.visibility;
    }
}