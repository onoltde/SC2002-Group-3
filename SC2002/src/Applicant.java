public class Applicant extends User {

    private final String applicantId;

    public Applicant (String name, String nric, int age, MaritalStatus maritalStatus, String password){
        super(name, nric, age, maritalStatus, password);
        this.applicantId = "AP-" + nric.substring(5);
    }

    public String getId(){return applicantId;}
}
