package Report;

import HdbManager.HdbManager;
import Applicant.Applicant;
import HdbOfficer.HdbOfficer;

public class ReportController {
    private ReportRepo reportRepo;
    private ReportUI reportUI;

    public ReportController() {
        reportRepo = new ReportRepo();
        reportUI = new ReportUI(this);
    }

    public ReportRepo getRepo() { return reportRepo; }
    public void saveChanges() { reportRepo.saveFile(); }
    public String showManagerMenu(HdbManager manager) { return reportUI.showManagerMenu(manager); }
    public String showOfficerMenu(HdbOfficer officer) { return reportUI.showOfficerMenu(officer); }
    public void displayApplicantReports(Applicant applicant) { reportUI.displayApplicantReports(applicant.getId()); }
}
