package Project;
import Applicant.*;
import Application.Residential.ResidentialApplicationController;
import Application.Residential.ResidentialApplicationRepo;
import Application.Team.TeamApplicationRepo;
import HdbManager.*;
import HdbOfficer.*;

import java.util.ArrayList;


public interface ProjectControllerInterface{

    public void saveChanges();

    public ProjectRepo getRepo();


    //applicant methods
    public void displayProjectDashboard(Applicant applicant, ResidentialApplicationController residentialApplicationController);

    //officer methods
    public String displayTeamProjectsToApply(HdbOfficer officer);

    public String displayResProjectsToApply(HdbOfficer officer, Flat.Type flatType);


    //manager methods
    public ArrayList<Object> displayProjectDashboard(HdbManager manager);

    public void displayProjectFlatDetails(String projectName, Flat.Type flatType);

    public void displayAdminProjectDetails(String projectName);
    
    public Project getProject(String projectName);

    //misc
    boolean bookFlat(String projectName, Flat.Type flatType);

}




