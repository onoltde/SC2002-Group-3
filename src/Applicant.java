import java.util.*;


public class Applicant extends User {

    private final String applicantId;
    private ResidentialApplication residentialApplication;
    private boolean hasResidentialApplication;
    private ArrayList<Enquiry> enquiries;

    public Applicant(String name, String nric, int age, MaritalStatus maritalStatus, String password, ResidentialApplication residentialApplication) {
        super(name, nric, age, maritalStatus, password);
        this.applicantId = "AP-" + nric.substring(5);
        this.hasResidentialApplication = (residentialApplication != null);
        this.residentialApplication = residentialApplication;
    }

    public String getId() {
        return applicantId;
    }

    @Override
    public String toString() {
        return "Applicant" + super.toString() + ", ID: " + applicantId + "}" ;
    }

    public ResidentialApplication getResidentialApplication() {
        return residentialApplication;
    }

    public boolean hasResidentialApplication(){
        return hasResidentialApplication;
    }

    public boolean canApplyTwoRoom() {
        return (getMaritalStatus() == MaritalStatus.MARRIED && getAge() >= 21);
    }

    public boolean canApplyThreeRoom(){
        return (canApplyTwoRoom() || (getMaritalStatus() == MaritalStatus.SINGLE && getAge() >= 35));
    }


}//end of class

