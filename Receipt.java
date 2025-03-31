package Project;

class Receipt {
    private String applicantName;
    private String nric;
    private String flatType;
    private String projectDetails;

    public Receipt(String applicantName, String nric, String flatType, String projectDetails) {
        this.applicantName = applicantName;
        this.nric = nric;
        this.flatType = flatType;
        this.projectDetails = projectDetails;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getNric() {
        return nric;
    }

    public String getFlatType() {
        return flatType;
    }

    public String getProjectDetails() {
        return projectDetails;
    }
}