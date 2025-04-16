package Application.Residential;
import Application.*;
import java.util.HashMap;

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

}
