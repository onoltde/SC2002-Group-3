/**
 * Represents an Applicant who can apply for BTO projects and related operations.
 * Extends the User class to inherit basic user properties.
 */

public class Applicant extends User {

    private final String applicantId;
    /**
     * Constructor for Applicant
     * @param name Applicant's name
     * @param nric Applicant's NRIC
     * @param age Applicant's age
     * @param maritalStatus Applicant's marital status           
     * @param password Applicant's password
     */
    public Applicant (String name, String nric, int age, User.MaritalStatus maritalStatus, String password){
        super(name, nric, age, maritalStatus, password);
        this.applicantId = "AP-" + nric.substring(5);
    }

    public String getId(){return applicantId;}

    @Override
    public String toString() {
        return "Applicant" + super.toString() + ", ID: " + applicantId + "}" ;
    }
}
