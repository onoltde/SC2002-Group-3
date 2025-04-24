package Application.Team;
import Application.*;

/**
 * Represents a team application for HDB projects.
 * This class implements the Application interface and handles the application process
 * for officers to join project teams, including status management.
 */
public class TeamApplication implements Application{

    private final String officerID;
    private final String projectName;
    private Application.Status status;

    /**
     * Constructs a new TeamApplication with the specified details.
     *
     * @param officerID The ID of the officer applying
     * @param projectName The name of the project being applied for
     * @param status The initial status of the application
     */
    public TeamApplication(String officerID,String projectName, Status status){
        this.officerID = officerID;
        this.projectName = projectName;
        this.status = status;
    }

    /**
     * Gets the name of the project being applied for.
     *
     * @return The project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Gets the current status of the application.
     *
     * @return The application status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Updates the status of the application.
     *
     * @param status The new status to set
     */
    public void updateStatus(Application.Status status) { 
        this.status = status; 
    }

    /**
     * Gets the ID of the officer who applied.
     *
     * @return The officer ID
     */
    public String getOfficerID(){
        return officerID;
    }
}
