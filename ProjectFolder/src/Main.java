public class Main {
    public static void main(String[] args) {
        ApplicantController applicantManager = new ApplicantController();
        Applicant guy = applicantManager.getApplicant("AP-543C");
        System.out.println(guy.getNric());
        applicantManager.addApplicant("Jeremy","T0424955F",22,User.MaritalStatus.SINGLE,"Password123");
        guy = applicantManager.getApplicant("AP-955F");
        System.out.println(guy.getNric());
        applicantManager.saveFile();
    }
}