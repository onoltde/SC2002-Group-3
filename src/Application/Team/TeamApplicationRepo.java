package Application.Team;
import Application.*;
import Application.Residential.ResidentialApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TeamApplicationRepo implements ApplicationRepo<TeamApplication> {

    //hashmap -> officerID : teamApplication
    private HashMap<String, TeamApplication > teamApplications;

    public TeamApplicationRepo(){
        teamApplications = new HashMap<String, TeamApplication>();
    }

    public void addApplication(TeamApplication newApplication) {
        teamApplications.put(newApplication.getOfficerID(),newApplication);
    }

    public HashMap<String, TeamApplication> getApplications(){
        return teamApplications;
    }

    public void deleteApplication(String officerID){
        teamApplications.remove(officerID);
    }

    // filter
    public ArrayList<TeamApplication> filterByProjectName(String projectName) {
        return teamApplications.values().stream()
                .filter(application -> application.getProjectName().equals(projectName))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
