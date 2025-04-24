package Report;

import HdbManager.HdbManager;
import Applicant.Applicant;
import HdbOfficer.HdbOfficer;

/**
 * Controls the interaction between the report data, UI, and external users such as managers and officers.
 */
public class ReportController {
    private ReportRepo reportRepo;
    private ReportUI reportUI;

    /**
     * Constructs a ReportController and initializes the repository and UI.
     */
    public ReportController() {
        reportRepo = new ReportRepo();
        reportUI = new ReportUI(this);
    }

    /**
     * @return the ReportRepo instance
     */
    public ReportRepo getRepo() {
        return reportRepo;
    }

    /**
     * Saves all changes made to the report repository.
     */
    public void saveChanges() {
        reportRepo.saveFile();
    }

    /**
     * Displays the menu for an HDB manager.
     *
     * @param manager the HDB manager
     * @return menu options as a formatted string
     */
    public String showManagerMenu(HdbManager manager) {
        return reportUI.showManagerMenu(manager);
    }

    /**
     * Displays the menu for an HDB officer.
     *
     * @param officer the HDB officer
     * @return menu options as a formatted string
     */
    public String showOfficerMenu(HdbOfficer officer) {
        return reportUI.showOfficerMenu(officer);
    }

    /**
     * Displays all reports associated with a specific applicant.
     *
     * @param applicant the applicant whose reports are to be shown
     */
    public void displayApplicantReports(Applicant applicant) {
        reportUI.displayApplicantReports(applicant.getId());
    }
}