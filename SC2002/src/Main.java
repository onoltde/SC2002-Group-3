public class Main {
    public static void main(String[] args) {
        ApplicantManager applicantManager = new ApplicantManager();
        applicantManager.loadFile();
        Applicant guy = applicantManager.getApplicant("AP-543C");
        System.out.println(guy.getNric());
    }
}