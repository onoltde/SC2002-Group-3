package Application.Residential;
import Application.*;
import Project.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Repository class for managing residential applications.
 * Implements the ApplicationRepo interface and provides methods to store, retrieve,
 * and filter residential applications using a HashMap data structure.
 */
public class ResidentialApplicationRepo implements ApplicationRepo<ResidentialApplication> {

    // HashMap --> userID : ResidentialApplication
    private HashMap<String, ResidentialApplication> residentialApplications;

    /**
     * Constructs a new ResidentialApplicationRepo with an empty HashMap.
     */
    public ResidentialApplicationRepo(){
        residentialApplications = new HashMap<String, ResidentialApplication>();
    }

    /**
     * Adds a new residential application to the repository.
     *
     * @param newResApplication The residential application to add
     */
    public void addApplication(ResidentialApplication newResApplication){
        residentialApplications.put(newResApplication.getApplicantId(), newResApplication);
    }

    /**
     * Retrieves all residential applications stored in the repository.
     *
     * @return HashMap containing all residential applications mapped by applicant ID
     */
    public HashMap<String, ResidentialApplication> getApplications(){
        return residentialApplications;
    }

    /**
     * Deletes a residential application from the repository.
     *
     * @param applicantID The ID of the applicant whose application should be deleted
     */
    public void deleteApplication(String applicantID){
        residentialApplications.remove(applicantID);
    }

    /**
     * Filters residential applications by project name.
     * Returns a list of applications that match the specified project name.
     *
     * @param projectName The name of the project to filter by
     * @return ArrayList of applications matching the project name
     */
    public ArrayList<ResidentialApplication> filterByProjectName(String projectName) {
        return residentialApplications.values().stream()
                .filter(application -> application.getProjectName().equals(projectName))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
