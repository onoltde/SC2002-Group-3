package HdbOfficer;

import java.util.*;
import Applicant.*;
import Application.Team.*;
import Application.Residential.*;
import Users.*;
import Project.*;

/**
 * Represents an HDB Officer, extending the {@link Applicant} class.
 * The officer manages residential and team applications, maintains a blacklist,
 * and handles project assignments.
 */
public class HdbOfficer extends Applicant {

    private final String officerId;
    private ArrayList<String> blacklist;     // List of previously applied projects as an applicant OR current application
    private boolean hasAssignedProject;
    private String assignedProjectName;
    private boolean hasTeamApplication;
    private TeamApplication teamApplication;

    /**
     * Constructs an HDB Officer with specific details.
     *
     * @param name                 The officer's name.
     * @param nric                 The officer's NRIC.
     * @param age                  The officer's age.
     * @param maritalStatus        The officer's marital status.
     * @param password             The officer's password.
     * @param residentialApplication The officer's residential application.
     * @param blacklist            The list of previously applied projects.
     * @param hasAssignedProject   Flag indicating if the officer has been assigned a project.
     * @param assignedProjectName  The name of the assigned project.
     * @param hasTeamApplication   Flag indicating if the officer has a team application.
     * @param teamApplication      The team application the officer has applied for.
     */
    public HdbOfficer(String name, String nric, int age, User.MaritalStatus maritalStatus, String password,
                      ResidentialApplication residentialApplication, ArrayList<String> blacklist,
                      boolean hasAssignedProject, String assignedProjectName, boolean hasTeamApplication,
                      TeamApplication teamApplication) {
        super(name, nric, age, maritalStatus, password, residentialApplication);
        this.blacklist = blacklist;
        this.hasAssignedProject = hasAssignedProject;
        this.assignedProjectName = assignedProjectName;
        this.hasTeamApplication = hasTeamApplication;
        this.teamApplication = teamApplication;
        this.officerId = "OF-" + nric.substring(5);
    }

    /**
     * Gets the officer's unique identifier.
     *
     * @return The officer's ID.
     */
    public String getId() {
        return officerId;
    }

    /**
     * Gets the list of projects the officer has previously applied to or is currently blacklisted from.
     *
     * @return The officer's blacklist.
     */
    public ArrayList<String> getBlacklist() {
        return blacklist;
    }

    /**
     * Checks if the officer has been assigned a project.
     *
     * @return True if the officer has an assigned project, otherwise false.
     */
    public boolean hasAssignedProject() {
        return hasAssignedProject;
    }

    /**
     * Assigns a project to the officer.
     *
     * @param projectName The name of the project to assign.
     */
    public void assignProject(String projectName) {
        hasAssignedProject = true;
        hasTeamApplication = false;
        assignedProjectName = projectName;
        blacklist.add(projectName);
    }

    /**
     * Gets the name of the project currently assigned to the officer.
     *
     * @return The name of the assigned project.
     */
    public String getAssignedProjectName() {
        return assignedProjectName;
    }

    /**
     * Checks if the officer has a team application.
     *
     * @return True if the officer has a team application, otherwise false.
     */
    public boolean hasTeamApplication() {
        return hasTeamApplication;
    }

    /**
     * Gets the officer's team application.
     *
     * @return The officer's team application.
     */
    public TeamApplication getTeamApplication() {
        return teamApplication;
    }

    /**
     * Returns a string representation of the officer.
     *
     * @return A string representation of the officer, including the officer ID.
     */
    @Override
    public String toString() {
        return "Officer" + super.toString() + ", ID: " + officerId + "}";
    }

    /**
     * Marks the officer as having applied for a team and adds the project to their blacklist.
     *
     * @param ta The team application the officer has applied for.
     */
    public void appliedTeam(TeamApplication ta) {
        this.hasTeamApplication = true;
        this.teamApplication = ta;
        if (blacklist.isEmpty() || blacklist.contains(" ")) {
            blacklist.clear();
        }
        this.blacklist.add(ta.getProjectName());
    }

    /**
     * Adds a project to the officer's blacklist.
     *
     * @param projName The project name to add to the blacklist.
     */
    public void addToBlackList(String projName) {
        this.blacklist.add(projName);
    }
}