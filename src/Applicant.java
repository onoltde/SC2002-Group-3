import java.util.*;


public class Applicant extends User {

    private final String applicantId;
    private Application application;
    private ArrayList<Enquiry> enquiries;

    public Applicant(String name, String nric, int age, MaritalStatus maritalStatus, String password,Application application) {
        super(name, nric, age, maritalStatus, password);
        this.applicantId = "AP-" + nric.substring(5);
        this.application = application;
    }

    public String getId() {
        return applicantId;
    }

    @Override
    public String toString() {
        return "Applicant" + super.toString() + ", ID: " + applicantId + "}" ;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

}

