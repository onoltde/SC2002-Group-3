/**
 * Represents an Applicant who can apply for BTO projects and related operations.
 * Extends the User class to inherit basic user properties.
 */
public class Applicant extends User {

    private final String applicantId;
    private Application application;
    private BookedFlat bookedFlat;

    /**
     * Constructor for Applicant.
     * @param name Applicant's name
     * @param nric Applicant's NRIC
     * @param age Applicant's age
     * @param maritalStatus Applicant's marital status
     * @param password Applicant's password
     */
    public Applicant(String name, String nric, int age, MaritalStatus maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.applicantId = "AP-" + nric.substring(5);
        this.application = null;
        this.bookedFlat = null;
    }

    public String getId() {
        return applicantId;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public BookedFlat getBookedFlat() {
        return bookedFlat;
    }

    public void setBookedFlat(BookedFlat bookedFlat) {
        this.bookedFlat = bookedFlat;
    }
}

