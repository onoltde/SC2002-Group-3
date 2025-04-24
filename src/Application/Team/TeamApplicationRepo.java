package Application.Team;
import Application.*;
import Application.Residential.ResidentialApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Repository class for managing team applications.
 * Implements the ApplicationRepo interface and provides methods to store, retrieve,
 * and filter team applications using a HashMap data structure.
 */
public class TeamApplicationRepo implements ApplicationRepo<TeamApplication> {

    //hashmap -> officerID : teamApplication
    private HashMap<String, TeamApplication > teamApplications;

    /**
     * Constructs a new TeamApplicationRepo with an empty HashMap.
     */
    public TeamApplicationRepo(){
        teamApplications = new HashMap<String, TeamApplication>();
    }

    /**
     * Adds a new team application to the repository.
     *
     * @param newApplication The team application to add
     */
    public void addApplication(TeamApplication newApplication) {
        teamApplications.put(newApplication.getOfficerID(),newApplication);
    }

    /**
     * Retrieves all team applications stored in the repository.
     *
     * @return HashMap containing all team applications mapped by officer ID
     */
    public HashMap<String, TeamApplication> getApplications(){
        return teamApplications;
    }

    /**
     * Deletes a team application from the repository.
     *
     * @param officerID The ID of the officer whose application should be deleted
     */
    public void deleteApplication(String officerID){
        teamApplications.remove(officerID);
    }

    /**
     * Filters team applications by project name.
     * Returns a list of applications that match the specified project name.
     *
     * @param projectName The name of the project to filter by
     * @return ArrayList of applications matching the project name
     */
    public ArrayList<TeamApplication> filterByProjectName(String projectName) {
        return teamApplications.values().stream()
                .filter(application -> application.getProjectName().equals(projectName))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
