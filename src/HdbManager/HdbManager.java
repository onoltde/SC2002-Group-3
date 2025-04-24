package HdbManager;

import Project.*;
import Users.*;

/**
 * Represents an HDB Manager, which is a specialized user who manages a specific project.
 */
public class HdbManager extends User {

    private final String managerId;
    private Project managedProject;

    /**
     * Constructs an HDB Manager with a name, NRIC, age, marital status, password, and a managed project.
     *
     * @param name            The name of the manager.
     * @param nric            The NRIC of the manager.
     * @param age             The age of the manager.
     * @param maritalStatus   The marital status of the manager.
     * @param password        The password for the manager's account.
     * @param managedProject  The project that the manager is assigned to.
     */
    public HdbManager(String name, String nric, int age, MaritalStatus maritalStatus, String password, Project managedProject) {
        super(name, nric, age, maritalStatus, password);
        this.managerId = "MA-" + nric.substring(5);  // Manager ID format: "MA-" + last part of NRIC
        this.managedProject = managedProject;
    }

    /**
     * Gets the unique ID of the manager.
     *
     * @return The manager's unique ID.
     */
    public String getId() {
        return this.managerId;
    }

    /**
     * Gets the project managed by the manager.
     *
     * @return The managed project, or null if no project is assigned.
     */
    public Project getManagedProject() {
        return managedProject;
    }

    /**
     * Gets the name of the project managed by the manager.
     *
     * @return The name of the managed project, or null if no project is assigned.
     */
    public String getManagedProjectName() {
        return (managedProject == null ? null : managedProject.getName());
    }

    /**
     * Returns a string representation of the manager.
     *
     * @return A string representation of the manager, including basic user info and manager-specific details.
     */
    @Override
    public String toString() {
        return "Manager" + super.toString() + ", ID: " + managerId + "}";
    }

    /**
     * Sets the project managed by the manager.
     *
     * @param managedProject The project to be assigned to the manager.
     */
    public void setManagedProject(Project managedProject) {
        this.managedProject = managedProject;
    }

    /**
     * Determines whether the manager can manage a given project.
     * A manager can manage a new project if they are not already managing another project,
     * or if the current managed project's close date is before the new project's open date.
     *
     * @param project The project to check.
     * @return True if the manager can manage the project, otherwise false.
     */
    public boolean canManage(Project project) {
        if (this.managedProject == null) return true;
        if (managedProject.getCloseDate().isBefore(project.getOpenDate())) return true;
        return false;
    }
}
