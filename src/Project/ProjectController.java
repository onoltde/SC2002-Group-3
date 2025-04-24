package Project;
import Applicant.*;
import Application.Residential.ResidentialApplicationController;
import Application.Residential.ResidentialApplicationRepo;
import Application.Team.TeamApplicationRepo;
import Enquiry.EnquiryController;
import HdbManager.*;
import HdbOfficer.*;
import Users.*;
import Application.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Manages the interactions and operations related to projects, including displaying dashboards,
 * applying for flats, managing officers, and updating project details.
 */
public class ProjectController implements ProjectControllerInterface {

    private static ProjectUI projectUI;
    private static ProjectRepo projectRepo;
    private HashMap<String, HdbManager> managerMap;
    private HashMap<String, HdbOfficer> officerMap;

    /**
     * Constructor for initializing the ProjectController.
     * It sets up the project repository, UI, and officer/manager mappings.
     */
    public ProjectController() {
        projectRepo = new ProjectRepo();
        projectUI = new ProjectUI(this);
        managerMap = new HdbManagerRepo(this).getManagers();
        officerMap = new HdbOfficerRepo(new ResidentialApplicationRepo(), new TeamApplicationRepo()).getOfficers();
    }

    /**
     * Saves any changes made to the project repository.
     */
    public void saveChanges(){
        projectRepo.saveProjects();
    }

    /**
     * Gets the project repository.
     * @return The project repository instance.
     */
    public ProjectRepo getRepo(){
        return projectRepo;
    }

    /**
     * Retrieves a project by its name.
     * @param projectName The name of the project.
     * @return The project with the specified name.
     */
    public Project getProject(String projectName) {
        return projectRepo.getProject(projectName);
    }

    // Applicant methods

    /**
     * Displays the project dashboard for an applicant.
     * @param applicant The applicant viewing the dashboard.
     * @param residentialApplicationController The controller managing residential applications.
     */
    public void displayProjectDashboard(Applicant applicant, ResidentialApplicationController residentialApplicationController) {
        projectUI.displayProjectDashboard(applicant, residentialApplicationController);
    }

    /**
     * Allows an applicant to apply for a project.
     * @param applicant The applicant applying for the project.
     * @param currentProject The project the applicant wants to apply to.
     * @param flatType The type of flat the applicant is applying for.
     */
    public void applyProject(Applicant applicant, Project currentProject, Flat.Type flatType){
        ResidentialApplicationController resAppController = new ResidentialApplicationController(this);
        resAppController.applyProject(applicant, currentProject, flatType);
    }

    // Officer methods

    /**
     * Displays the list of team projects available for an officer to apply to.
     * @param officer The officer viewing available projects.
     * @return A string containing the available projects for the officer.
     */
    public String displayTeamProjectsToApply(HdbOfficer officer){
        return projectUI.displayTeamProjectsToApply(officer);
    }

    /**
     * Displays the list of residential projects available for an officer to apply to, based on flat type.
     * @param officer The officer viewing available projects.
     * @param flatType The type of flat (2-room or 3-room) the officer is interested in.
     * @return The project name for the residential project.
     */
    public String displayResProjectsToApply(HdbOfficer officer, Flat.Type flatType){
        String projName = "";
        if (flatType == Flat.Type.TWOROOM){
            projName = projectUI.displayTwoRoomResProjectsToApply(officer);
        } else if (flatType == Flat.Type.THREEROOM){
            projName = projectUI.displayThreeRoomResProjectsToApply(officer);
        }
        return projName;
    }

    /**
     * Books a flat in a project.
     * @param projectName The name of the project where the flat is located.
     * @param flatType The type of flat being booked.
     * @return true if the booking is successful, false otherwise.
     */
    public boolean bookFlat(String projectName, Flat.Type flatType){
        Project project = projectRepo.getProject(projectName);
        Flat flat = project.getFlatInfo().get(flatType);
        return flat.bookUnit();
    }

    // Manager methods

    /**
     * Displays the project dashboard for a manager.
     * @param manager The manager viewing the dashboard.
     * @return A list of objects to display on the manager's dashboard.
     */
    public ArrayList<Object> displayProjectDashboard(HdbManager manager) {
        return projectUI.displayProjectDashboard(manager);
    }

    // Project UI methods

    /**
     * Displays the details of a specific flat type in a project.
     * @param projectName The name of the project.
     * @param flatType The type of flat (2-room or 3-room) to display details for.
     */
    public void displayProjectFlatDetails(String projectName, Flat.Type flatType){
        Project project = projectRepo.getProject(projectName);
        projectUI.displayEssentialProjectDetails(project);
        if (flatType == Flat.Type.TWOROOM){
            projectUI.displayFlatDetails(project.getFlatInfo().get(Flat.Type.TWOROOM));
        } else if (flatType == Flat.Type.THREEROOM){
            projectUI.displayFlatDetails(project.getFlatInfo().get(Flat.Type.THREEROOM));
        }
    }

    /**
     * Displays detailed project information for an admin.
     * @param projectName The name of the project.
     */
    public void displayAdminProjectDetails(String projectName){
        Project project = projectRepo.getProject(projectName);
        projectUI.displayEssentialProjectDetails(project);
        projectUI.displayFlatDetails(project.getFlatInfo().get(Flat.Type.TWOROOM));
        projectUI.displayFlatDetails(project.getFlatInfo().get(Flat.Type.THREEROOM));
        projectUI.displayProjectAdminDetails(project, managerMap, officerMap);
    }
}