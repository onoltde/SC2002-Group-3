package Application.Residential;
import Application.*;
import Project.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ResidentialApplicationRepo implements ApplicationRepo<ResidentialApplication> {

    // HashMap --> userID : ResidentialApplication
    private HashMap<String, ResidentialApplication> residentialApplications;

    public ResidentialApplicationRepo(){
        residentialApplications = new HashMap<String, ResidentialApplication>();
    }

    public void addApplication(ResidentialApplication newResApplication){
        residentialApplications.put(newResApplication.getApplicantId(), newResApplication);
    }

    public HashMap<String, ResidentialApplication> getApplications(){
        return residentialApplications;
    }

    public void deleteApplication(String applicantID){
        residentialApplications.remove(applicantID);
    }

    //for officer/manager to approve applications
    //filters
    public ArrayList<ResidentialApplication> filterByProjectName(String projectName) {
        return residentialApplications.values().stream()
                .filter(application -> application.getProjectName().equals(projectName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
