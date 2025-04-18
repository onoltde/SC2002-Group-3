package Applicant;
import Users.*;
import Application.Residential.*;
import Enquiry.*;
import Project.*;
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

    public boolean canApply(Flat.Type flatType){
        if (flatType == Flat.Type.TWOROOM){
            return canApplyTwoRoom();
        }else if (flatType == Flat.Type.THREEROOM){
            return canApplyThreeRoom();
        }
        return false;
    }

    private boolean canApplyTwoRoom() {
        if(getMaritalStatus() == MaritalStatus.SINGLE){
            return (getAge() >= 35);
        }else if ( getMaritalStatus() == MaritalStatus.MARRIED){
            return (getAge() >= 21);
        }
        return false; //default
    }

    private boolean canApplyThreeRoom(){
        if(getMaritalStatus() == MaritalStatus.SINGLE){
            return false;
        }else if ( getMaritalStatus() == MaritalStatus.MARRIED){
            return (getAge() >= 21);
        }
        return false; //default
    }

    public void newApplication(ResidentialApplication newApplication){
        this.residentialApplication = newApplication;
        this.hasResidentialApplication = true;
    }


}//end of class

