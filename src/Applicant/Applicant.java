package Applicant;
import Users.*;
import Application.Residential.*;
import Enquiry.*;
import Project.*;
import java.util.*;

/**
 * Represents an Applicant role in the system.
 * An Applicant can apply for residential housing and make enquiries.
 */
public class Applicant extends User {

    private final String applicantId;
    private ResidentialApplication residentialApplication;
    private boolean hasResidentialApplication;
    private ArrayList<Enquiry> enquiries;

     /**
     * Constructs an Applicant with the specified details.
     *
     * @param name                   the applicant's name
     * @param nric                   the applicant's NRIC
     * @param age                    the applicant's age
     * @param maritalStatus          the applicant's marital status
     * @param password               the applicant's password
     * @param residentialApplication the applicant's existing residential application (can be null)
     */
    public Applicant(String name, String nric, int age, MaritalStatus maritalStatus, String password, ResidentialApplication residentialApplication) {
        super(name, nric, age, maritalStatus, password);
        this.applicantId = "AP-" + nric.substring(5);
        this.hasResidentialApplication = (residentialApplication != null);
        this.residentialApplication = residentialApplication;
    }


     /**
     * Returns the unique ID of the applicant.
     *
     * @return the applicant ID
     */
    public String getId() {
        return applicantId;
    }

    /**
     * Returns a string representation of the applicant.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "Applicant" + super.toString() + ", ID: " + applicantId + "}" ;
    }

    /**
     * Returns the applicant's residential application.
     *
     * @return the residential application object
     */
    public ResidentialApplication getResidentialApplication() {
        return residentialApplication;
    }

     /**
     * Indicates whether the applicant has a residential application.
     *
     * @return true if there is an application, false otherwise
     */
    public boolean hasResidentialApplication(){
        return hasResidentialApplication;
    }
    /**
     * Checks if the applicant is eligible to apply for the given flat type.
     *
     * @param flatType the type of flat to check eligibility for
     * @return true if eligible, false otherwise
     */

    public boolean canApply(Flat.Type flatType){
        if (flatType == Flat.Type.TWOROOM){
            return canApplyTwoRoom();
        }else if (flatType == Flat.Type.THREEROOM){
            return canApplyThreeRoom();
        }
        return false;
    }

     /**
     * Internal check for eligibility to apply for a two-room flat.
     *
     * @return true if eligible, false otherwise
     */
    private boolean canApplyTwoRoom() {
        if(getMaritalStatus() == MaritalStatus.SINGLE){
            return (getAge() >= 35);
        }else if ( getMaritalStatus() == MaritalStatus.MARRIED){
            return (getAge() >= 21);
        }
        return false; //default
    }

    /**
     * Internal check for eligibility to apply for a three-room flat.
     *
     * @return true if eligible, false otherwise
     */
    private boolean canApplyThreeRoom(){
        if(getMaritalStatus() == MaritalStatus.SINGLE){
            return false;
        }else if ( getMaritalStatus() == MaritalStatus.MARRIED){
            return (getAge() >= 21);
        }
        return false; //default
    }
    /**
     * Registers a new residential application for the applicant.
     *
     * @param newApplication the new residential application to associate with the applicant
     */

    public void newApplication(ResidentialApplication newApplication){
        this.residentialApplication = newApplication;
        this.hasResidentialApplication = true;
    }


}//end of class

