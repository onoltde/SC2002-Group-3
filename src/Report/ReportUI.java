package Report;

import java.util.ArrayList;
import Project.*;
import Users.*;

public class ReportUI {
    private ReportController reportController;

    public ReportUI(ReportController reportController) {
        this.reportController = reportController;
    }

    public void displayApplicantReports(String applicantId) {
        ArrayList<Report> reports = reportController.getRepo().getReportsByApplicant(applicantId);
        displayReports(reports, "Reports for Applicant ID: " + applicantId);
    }

    public void displayAllReports() {
        ArrayList<Report> reports = new ArrayList<>(reportController.getRepo().getReports().values());
        displayReports(reports, "All Reports");
    }

    public void displayReportsByMaritalStatus(User.MaritalStatus status) {
        ArrayList<Report> reports = reportController.getRepo().getReportsByMaritalStatus(status);
        displayReports(reports, "Reports with Marital Status: " + status);
    }

    public void displayReportsByFlatType(Flat.Type flatType) {
        ArrayList<Report> reports = reportController.getRepo().getReportsByFlatType(flatType);
        displayReports(reports, "Reports for Flat Type: " + flatType);
    }

    private void displayReports(ArrayList<Report> reports, String title) {
        System.out.println("\n--------- " + title + " ---------");

        if (reports.isEmpty()) {
            System.out.println("No reports found.");
            return;
        }

        for (Report e : reports) {
            System.out.println(e);
            System.out.println("--------------------------------");
        }
    }
}
