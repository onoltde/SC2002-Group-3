package Project;

class Applicant {
    private String nric;
    private int age;
    private String maritalStatus;
    private String password;

    public Applicant(String nric, int age, String maritalStatus, String password) {
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    public String getNric() {
        return nric;
    }

    public int getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getPassword() {
        return password;
    }
}