package HdbManager;
import Project.*;
import Users.*;

public class HdbManager extends User {

    private final String managerId;
    private Project managedProject;
    
    public HdbManager(String name,String nric, int age, MaritalStatus maritalStatus, String password, Project managedProject) {
        super(name, nric, age, maritalStatus, password);
        this.managerId = "MA-" + nric.substring(5);
        this.managedProject = managedProject;
    }

    public String getId(){ return this.managerId; }
    public Project getManagedProject() { return managedProject; }

    @Override
    public String toString() { return "Manager" + super.toString() + ", ID: " + managerId + "}"; }

    public void setManagedProject(Project managedProject) { this.managedProject = managedProject; }

    public boolean canManage(Project project) {
        if(this.managedProject == null) return true;
        if(managedProject.getCloseDate().isBefore(project.getOpenDate())) return true;
        return false;
    }
}