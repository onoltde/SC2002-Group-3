package Project;
import Applicant.*;
import Application.Residential.ResidentialApplicationController;
import Application.Residential.ResidentialApplicationRepo;
import Application.Team.TeamApplicationRepo;
import HdbManager.*;
import HdbOfficer.*;

import java.util.ArrayList;


/**
 * Interface that defines the core functionality for controlling and interacting with housing development projects.
 * This includes operations for applicants, officers, managers, and general project data handling.
 */
public interface ProjectControllerInterface {

    /**
     * Saves any modifications made to the project repository.
     */
    public void saveChanges();

    /**
     * Retrieves the repository containing all project data.
     * @return the ProjectRepo instance.
     */
    public ProjectRepo getRepo();

    // Applicant methods

    /**
     * Displays the project dashboard for a specific applicant.
     * @param applicant the applicant whose dashboard is being displayed.
     * @param residentialApplicationController the controller handling residential applications.
     */
    public void displayProjectDashboard(Applicant applicant, ResidentialApplicationController residentialApplicationController);

    // Officer methods

    /**
     * Displays the list of team-based projects that an officer can apply to.
     * @param officer the HdbOfficer requesting the view.
     * @return the project names available to the officer.
     */
    public String displayTeamProjectsToApply(HdbOfficer officer);

    /**
     * Displays the list of residential projects that an officer can apply to, based on flat type.
     * @param officer the HdbOfficer applying.
     * @param flatType the type of flat (e.g., TWOROOM, THREEROOM).
     * @return the name of the project that the officer can apply to.
     */
    public String displayResProjectsToApply(HdbOfficer officer, Flat.Type flatType);

    // Manager methods

    /**
     * Displays the project dashboard for a given manager.
     * @param manager the HdbManager viewing the dashboard.
     * @return a list of objects to be displayed on the manager's dashboard.
     */
    public ArrayList<Object> displayProjectDashboard(HdbManager manager);

    /**
     * Displays detailed flat information of a given type for a specific project.
     * @param projectName the name of the project.
     * @param flatType the type of flat to display details for.
     */
    public void displayProjectFlatDetails(String projectName, Flat.Type flatType);

    /**
     * Displays all relevant administrative project details including flat and personnel assignments.
     * @param projectName the name of the project.
     */
    public void displayAdminProjectDetails(String projectName);

    /**
     * Retrieves a project by name.
     * @param projectName the name of the project.
     * @return the Project object corresponding to the given name.
     */
    public Project getProject(String projectName);

    // Miscellaneous

    /**
     * Attempts to book a flat of a specified type in a given project.
     * @param projectName the name of the project.
     * @param flatType the type of flat to be booked.
     * @return true if the booking was successful, false otherwise.
     */
    public boolean bookFlat(String projectName, Flat.Type flatType);
}
