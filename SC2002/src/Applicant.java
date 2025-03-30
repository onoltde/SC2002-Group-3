public class Applicant extends User {

    private String applicantId;


    public Applicant (String name, String nric, int age, MaritalStatus maritalStatus){
        super(name, nric, age, maritalStatus);
        this.applicantId = ApplicantIdGen.generateId();
    }

}
