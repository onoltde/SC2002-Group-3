package Application.Team;

import Application.*;

/**
 * Represents a team-based housing application within the BTO system.
 * This class implements the {@link Application} interface, storing information about the application
 * including the officer's ID, project name, and the application's status.
 */
public class TeamApplication implements Application {

    /** The ID of the officer responsible for the application. */
    private final String officerID;

    /** The name of the BTO project associated with the application. */
    private final String projectName;

    /** The current status of the application. */
    private Application.Status status;

    /**
     * Constructs a TeamApplication with the specified officer ID, project name, and application status.
     *
     * @param officerID the ID of the officer handling the application
     * @param projectName the name of the BTO project
     * @param status the current status of the application
     */
    public TeamApplication(String officerID, String projectName, Status status) {
        this.officerID = officerID;
        this.projectName = projectName;
        this.status = status;
    }

    /**
     * Retrieves the name of the BTO project associated with the application.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Retrieves the current status of the application.
     *
     * @return the application status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Updates the status of the application.
     *
     * @param status the new status of the application
     */
    public void updateStatus(Application.Status status) {
        this.status = status;
    }

    /**
     * Retrieves the ID of the officer responsible for the application.
     *
     * @return the officer's ID
     */
    public String getOfficerID() {
        return officerID;
    }
}
