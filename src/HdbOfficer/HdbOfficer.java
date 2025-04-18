package HdbOfficer;
import java.util.*;
import Applicant.*;
import Application.Team.*;
import Application.Residential.*;
import Users.*;
import Project.*;

public class HdbOfficer extends Applicant{

    private final String officerId;
    private ArrayList<String> blacklist;     //list of previously applied project as applicant OR current application
    private boolean hasAssignedProject;
    private String assignedProjectName;
    private boolean hasTeamApplication;
    private TeamApplication teamApplication;


    public HdbOfficer(String name, String nric, int age, User.MaritalStatus maritalStatus, String password, ResidentialApplication residentialApplication, ArrayList<String> blacklist, boolean hasAssignedProject, String assignedProjectName, boolean hasTeamApplication, TeamApplication teamApplication) {
        super(name, nric, age, maritalStatus, password, residentialApplication);
        this.blacklist = blacklist;
        this.hasAssignedProject = hasAssignedProject;
        this.assignedProjectName = assignedProjectName;
        this.hasTeamApplication = hasTeamApplication;
        this.teamApplication = teamApplication;
        this.officerId = "OF-" + nric.substring(5);
    }

    public String getId(){ return officerId; }

    public ArrayList<String> getBlacklist(){
        return blacklist;
    }

    public boolean hasAssignedProject() {
        return hasAssignedProject;
    }
    public void assignProject(String projectName) {
        hasAssignedProject = true;
        hasTeamApplication = false;
        assignedProjectName = projectName;
        blacklist.add(projectName);
    }

    public String getAssignedProjectName() {
        return assignedProjectName;
    }

    public boolean hasTeamApplication() {
        return hasTeamApplication;
    }

    public TeamApplication getTeamApplication() {
        return teamApplication;
    }

    @Override
    public String toString() {
        return "Officer" + super.toString() + ", ID: " + officerId + "}" ;
    }

    public void appliedTeam(TeamApplication ta) {
    	this.hasTeamApplication = true;
    	this.teamApplication = ta;
    	if (blacklist.isEmpty() || blacklist.contains(" ")) {
    		blacklist.clear();
    	}
    	this.blacklist.add(ta.getProjectName());
    }
    
    public void addToBlackList(String projName) {
    	this.blacklist.add(projName);
    }


}
