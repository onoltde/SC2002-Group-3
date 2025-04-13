package Application.Team;
import Application.*;

import java.util.HashMap;

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

}
