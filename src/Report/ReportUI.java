package Report;

import java.util.ArrayList;
import java.util.Scanner;

import HdbManager.HdbManager;
import HdbOfficer.HdbOfficer;
import Project.*;
import Users.*;
import Utility.InputUtils;

public class ReportUI {
    private ReportController reportController;

    public ReportUI(ReportController reportController) {
        this.reportController = reportController;
    }

    public String showManagerMenu(HdbManager manager) {
        System.out.println("\n======= Report Menu =======");
        System.out.println("1. View Reports");
        System.out.println("2. Generate Report");
        System.out.println("3. Back");
        System.out.print("Enter choice: ");

        int choice = InputUtils.readInt();


        switch(choice) {
            case 1 -> {
                viewReportMenu();
                return null;
            } case 2 -> {
                return generateReport();
            } case 3 -> {
                return "a";
            } default -> {
                System.out.println("Invalid choice!");
            }
        }
        return null;
    }

    public void viewReportMenu() {
        while(true) {
            System.out.println("\n======= Report Filter Menu =======");
            System.out.println("Filter by:");
            System.out.println("1. Applicant");
            System.out.println("2. Project");
            System.out.println("3. Marital status");
            System.out.println("4. Flat type");
            System.out.println("5. View All Reports");
            System.out.println("6. Back");
            System.out.print("Enter choice: ");

            int choice = InputUtils.readInt();

            switch(choice) {
                case 1:
                    System.out.print("Enter Applicant ID: ");
                    String applicantId = InputUtils.nextLine();
                    displayApplicantReports(applicantId);
                    break;
                case 2:
                    System.out.print("Enter Project Name: ");
                    String projectName = InputUtils.nextLine();
                    displayProjectReports(projectName);
                    break;
                case 3:
                    System.out.println("Select Marital Status:");
                    System.out.println("1. SINGLE");
                    System.out.println("2. MARRIED");
                    int statusChoice = InputUtils.readInt();

                    User.MaritalStatus status = statusChoice == 1 ? User.MaritalStatus.SINGLE : User.MaritalStatus.MARRIED;
                    displayReportsByMaritalStatus(status);
                    break;
                case 4:
                    System.out.println("Select Flat Type:");
                    System.out.println("1. Two Room");
                    System.out.println("2. Three Room");
                    int typeChoice = InputUtils.readInt();

                    Flat.Type flatType = typeChoice == 1 ? Flat.Type.TWOROOM : Flat.Type.THREEROOM;
                    displayReportsByFlatType(flatType);
                    break;
                case 5:
                    displayAllReports();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public String showOfficerMenu(HdbOfficer officer) {
        System.out.println("\n======= Report Menu =======");
        System.out.println("1. View Reports");
        System.out.println("2. Generate Report");
        System.out.println("3. Back");
        System.out.print("Enter choice: ");

        int choice = InputUtils.readInt();


        switch(choice) {
            case 1 -> {
                displayProjectReports(officer.getAssignedProjectName());
                return null;
            } case 2 -> {
                return generateReport();
            } case 3 -> {
                return "a";
            } default -> {
                System.out.println("Invalid choice!");
            }
        }
        return null;
    }

    public String generateReport() {
        System.out.print("Enter the applicant ID: ");
        return InputUtils.nextLine();
    }

    public void displayApplicantReports(String applicantId) {
        ArrayList<Report> reports = reportController.getRepo().getReportsByApplicant(applicantId);
        displayReports(reports, "Reports for Applicant ID: " + applicantId);
    }

    public void displayProjectReports(String projectName) {
        ArrayList<Report> reports = reportController.getRepo().getReportsByProject(projectName);
        displayReports(reports, "Reports for Project: " + projectName);
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
            InputUtils.printSmallDivider();
        }
    }
}
