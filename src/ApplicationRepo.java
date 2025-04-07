import java.util.HashMap;

public class ApplicationRepo {

    private HashMap<String,Application> applications;

    public ApplicationRepo(){
        applications = new HashMap<String,Application>();
    }

    public Application addApplication( String applicantId, Application.Status status, String projectName, Flat.Type flatType){
        Application application = new Application(applicantId,status,projectName,flatType);
        applications.put(projectName,application);
        return application;
    }

    public void printApplications(){
        for (Application applications : applications.values()){
            System.out.println(applications.toString());
        }
    }

}
