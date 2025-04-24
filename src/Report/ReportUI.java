package Report;

import java.util.ArrayList;
import HdbManager.HdbManager;
import Project.*;
import Users.*;
import Utility.InputUtils;

/**
 * User Interface for viewing reports.
 * 
 * Provides menu-driven options to filter reports by applicant, project, flat type, and marital status.
 * Acts as the "Boundary" in the Boundary–Entity–Controller (BEC) architecture.
 */
public class ReportUI {
    private ReportController reportController;

    /**
     * Constructor to initialize the ReportController.
     *
     * @param reportController The controller that manages the report logic and data.
     */
    public ReportUI(ReportController reportController) {
        this.reportController = reportController;
    }

    /**
     * Displays the main menu for the manager and handles user input to either view or generate reports.
     *
     * @param manager The HdbManager instance representing the current manager.
     * @return A string indicating the next action, or null if no action is taken.
     */
    public String showManagerMenu(HdbManager manager) {
        System.out.println("\n======= Report Menu =======");
        System.out.println("1. View Reports");
        System.out.println("2. Generate Report");
        System.out.println("3. Back");
        System.out.print("Enter choice: ");

        // Reading user input for menu choice
        int choice = InputUtils.readInt();

        // Switch statement for handling menu choices
        switch(choice) {
            case 1 -> {
                viewReportMenu(); // View report menu
                return null;
            } case 2 -> {
                return generateReport(manager); // Generate a report
            } case 3 -> {
                return "a"; // Go back to previous menu
            } default -> {
                System.out.println("Invalid choice!"); // Invalid input
            }
        }
        return null;
    }

    /**
     * Displays the report filter menu where the user can filter reports by applicant,
     * project, marital status, flat type, or view all reports.
     */
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

            // Reading user input for filter choice
            int choice = InputUtils.readInt();

            // Switch statement to handle filtering choices
            switch(choice) {
                case 1:
                    System.out.print("Enter Applicant ID: ");
                    String applicantId = InputUtils.nextLine();
                    displayApplicantReports(applicantId); // Display reports for a specific applicant
                    break;
                case 2:
                    System.out.print("Enter Project Name: ");
                    String projectName = InputUtils.nextLine();
                    displayProjectReports(projectName); // Display reports for a specific project
                    break;
                case 3:
                    System.out.println("Select Marital Status:");
                    System.out.println("1. SINGLE");
                    System.out.println("2. MARRIED");
                    int statusChoice = InputUtils.readInt();

                    // Set marital status based on user choice
                    User.MaritalStatus status = statusChoice == 1 ? User.MaritalStatus.SINGLE : User.MaritalStatus.MARRIED;
                    displayReportsByMaritalStatus(status); // Display reports based on marital status
                    break;
                case 4:
                    System.out.println("Select Flat Type:");
                    System.out.println("1. Two Room");
                    System.out.println("2. Three Room");
                    int typeChoice = InputUtils.readInt();

                    // Set flat type based on user choice
                    Flat.Type flatType = typeChoice == 1 ? Flat.Type.TWOROOM : Flat.Type.THREEROOM;
                    displayReportsByFlatType(flatType); // Display reports based on flat type
                    break;
                case 5:
                    displayAllReports(); // Display all reports
                    break;
                case 6:
                    return; // Exit back to the previous menu
                default:
                    System.out.println("Invalid choice!"); // Invalid input
            }
        }
    }

    /**
     * Prompts the user for an applicant ID and generates a report.
     *
     * @param manager The HdbManager instance representing the current manager.
     * @return The applicant ID entered by the user for report generation.
     */
    public String generateReport(HdbManager manager) {
        System.out.print("Enter the applicant ID: ");
        return InputUtils.nextLine(); // Return the applicant ID entered by the user
    }

    /**
     * Displays reports associated with a specific applicant based on the applicant ID.
     *
     * @param applicantId The ID of the applicant for whom to display the reports.
     */
    public void displayApplicantReports(String applicantId) {
        ArrayList<Report> reports = reportController.getRepo().getReportsByApplicant(applicantId);
        displayReports(reports, "Reports for Applicant ID: " + applicantId);
    }

    /**
     * Displays reports associated with a specific project based on the project name.
     *
     * @param projectName The name of the project for which to display the reports.
     */
    public void displayProjectReports(String projectName) {
        ArrayList<Report> reports = reportController.getRepo().getReportsByProject(projectName);
        displayReports(reports, "Reports for Project: " + projectName);
    }

    /**
     * Displays all reports from the report repository.
     */
    public void displayAllReports() {
        ArrayList<Report> reports = new ArrayList<>(reportController.getRepo().getReports().values());
        displayReports(reports, "All Reports");
    }

    /**
     * Displays reports filtered by marital status.
     *
     * @param status The marital status to filter reports by.
     */
    public void displayReportsByMaritalStatus(User.MaritalStatus status) {
        ArrayList<Report> reports = reportController.getRepo().getReportsByMaritalStatus(status);
        displayReports(reports, "Reports with Marital Status: " + status);
    }

    /**
     * Displays reports filtered by flat type.
     *
     * @param flatType The flat type to filter reports by.
     */
    public void displayReportsByFlatType(Flat.Type flatType) {
        ArrayList<Report> reports = reportController.getRepo().getReportsByFlatType(flatType);
        displayReports(reports, "Reports for Flat Type: " + flatType);
    }

    /**
     * Displays the list of reports along with a title.
     *
     * @param reports The list of reports to be displayed.
     * @param title The title of the report list (e.g., "Reports for Applicant ID: 12345").
     */
    private void displayReports(ArrayList<Report> reports, String title) {
        System.out.println("\n--------- " + title + " ---------");

        if (reports.isEmpty()) {
            System.out.println("No reports found."); // No reports found for the filter
            return;
        }

        // Display each report
        for (Report e : reports) {
            System.out.println(e);
            InputUtils.printSmallDivider(); // Print a divider between reports
        }
    }
}
