package Applicant;

import Users.*;
import Application.Residential.*;
import Enquiry.*;
import Project.*;
import java.util.*;

/**
 * Represents an applicant for residential applications, including personal details and associated enquiries.
 * Inherits from the User class and adds functionality for residential application and enquiry management.
 */
public class Applicant extends User {

    private final String applicantId;
    private ResidentialApplication residentialApplication;
    private boolean hasResidentialApplication;
    private ArrayList<Enquiry> enquiries;

    /**
     * Constructs an Applicant instance with the specified details.
     *
     * @param name The applicant's name.
     * @param nric The applicant's NRIC number.
     * @param age The applicant's age.
     * @param maritalStatus The marital status of the applicant.
     * @param password The applicant's password for authentication.
     * @param residentialApplication The applicant's residential application (can be null if no application exists).
     */
    public Applicant(String name, String nric, int age, MaritalStatus maritalStatus, String password, ResidentialApplication residentialApplication) {
        super(name, nric, age, maritalStatus, password);
        this.applicantId = "AP-" + nric.substring(5); // Extracting part of the NRIC for the applicant ID
        this.hasResidentialApplication = (residentialApplication != null); // Checks if the applicant has a residential application
        this.residentialApplication = residentialApplication;
        this.enquiries = new ArrayList<>(); // Initializes the list of enquiries
    }

    /**
     * Gets the unique ID of the applicant.
     *
     * @return The applicant's ID.
     */
    public String getId() {
        return applicantId;
    }

    /**
     * Returns a string representation of the applicant, including their details.
     *
     * @return The string representation of the applicant.
     */
    @Override
    public String toString() {
        return "Applicant" + super.toString() + ", ID: " + applicantId + "}";
    }

    /**
     * Gets the residential application associated with this applicant.
     *
     * @return The residential application of the applicant, or null if not available.
     */
    public ResidentialApplication getResidentialApplication() {
        return residentialApplication;
    }

    /**
     * Checks if the applicant has a residential application.
     *
     * @return True if the applicant has a residential application, false otherwise.
     */
    public boolean hasResidentialApplication() {
        return hasResidentialApplication;
    }

    /**
     * Checks if the applicant is eligible to apply for a flat of the specified type.
     *
     * @param flatType The type of flat the applicant is applying for.
     * @return True if the applicant is eligible, false otherwise.
     */
    public boolean canApply(Flat.Type flatType) {
        if (flatType == Flat.Type.TWOROOM) {
            return canApplyTwoRoom();
        } else if (flatType == Flat.Type.THREEROOM) {
            return canApplyThreeRoom();
        }
        return false;
    }

    /**
     * Checks if the applicant is eligible to apply for a two-room flat.
     * Eligibility is based on marital status and age.
     *
     * @return True if eligible, false otherwise.
     */
    private boolean canApplyTwoRoom() {
        if (getMaritalStatus() == MaritalStatus.SINGLE) {
            return (getAge() >= 35); // Single applicants must be at least 35 years old.
        } else if (getMaritalStatus() == MaritalStatus.MARRIED) {
            return (getAge() >= 21); // Married applicants must be at least 21 years old.
        }
        return false; // Default case, should not happen
    }

    /**
     * Checks if the applicant is eligible to apply for a three-room flat.
     * Eligibility is based on marital status and age.
     *
     * @return True if eligible, false otherwise.
     */
    private boolean canApplyThreeRoom() {
        if (getMaritalStatus() == MaritalStatus.SINGLE) {
            return false; // Single applicants cannot apply for a three-room flat.
        } else if (getMaritalStatus() == MaritalStatus.MARRIED) {
            return (getAge() >= 21); // Married applicants must be at least 21 years old.
        }
        return false; // Default case, should not happen
    }

    /**
     * Creates a new residential application for the applicant.
     *
     * @param newApplication The new residential application to associate with the applicant.
     */
    public void newApplication(ResidentialApplication newApplication) {
        this.residentialApplication = newApplication;
        this.hasResidentialApplication = true; // Marks that the applicant now has a residential application
    }
}