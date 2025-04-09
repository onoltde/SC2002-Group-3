import java.util.HashMap;

public class TeamApplicationRepo implements ApplicationRepoInterface <TeamApplication>{

    private HashMap<String, TeamApplication > teamApplications; // projectName : TeamApplicaiton

    public TeamApplicationRepo(){
        teamApplications = new HashMap<String, TeamApplication>();
    }

    public void addApplication(TeamApplication newApplication) {
        teamApplications.put(newApplication.getProjectName(),newApplication);
    }

}
