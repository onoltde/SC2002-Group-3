package Project;
import Applicant.*;
import Application.Residential.ResidentialApplicationRepo;
import Application.Team.TeamApplicationRepo;
import HdbManager.*;
import HdbOfficer.*;
import Users.*;
import Application.*;

import java.util.HashMap;

public interface ProjectControllerInterface{

    public void saveChanges();

    public ProjectRepo getRepo();


    //applicant methods
    public void displayProjectDashboard(Applicant applicant);

    //officer methods
    public String displayTeamProjectsToApply(HdbOfficer officer);

    public void displayResProjectsToApply(HdbOfficer officer, Flat.Type flatType);


    //manager methods
    public void displayProjectDashboard(HdbManager manager);

    public void displayProjectFlatDetails(String projectName, Flat.Type flatType);

    public void displayAdminProjectDetails(String projectName);
    
    public Project getProject(String projectName);

}




