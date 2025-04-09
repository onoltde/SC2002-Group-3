import java.util.HashMap;

public class ResidentialApplicationRepo implements ApplicationRepoInterface <ResidentialApplication>{

    private HashMap<String, ResidentialApplication> residentialApplications;    // projectName : ResidentialApplication

    public ResidentialApplicationRepo(){
        residentialApplications = new HashMap<String, ResidentialApplication>();
    }

    public void addApplication(ResidentialApplication newResApplication){
        residentialApplications.put(newResApplication.getProjectName(), newResApplication);
    }

}
