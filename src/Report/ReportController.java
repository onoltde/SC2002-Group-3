package Report;

import HdbManager.HdbManager;
/**
 * Controller class in the Boundary–Entity–Controller architecture.
 * 
 * Mediates between the ReportUI (boundary) and the ReportRepo/Report (entity).
 * Handles business logic such as generating, viewing, and managing reports
 * based on inputs from HDB managers.
 */
public class ReportController {
    private ReportRepo reportRepo;
    private ReportUI reportUI;
    /**
     * Initializes the ReportController, linking the repository (entity) and the UI (boundary).
     */
    public ReportController() {
        reportRepo = new ReportRepo();
        reportUI = new ReportUI(this);
    }
    /**
     * @return the report repository (entity layer) used by this controller
     */
    public ReportRepo getRepo() { return reportRepo; }
    /**
     * Saves all current report data to persistent storage (CSV file).
     */
    public void saveChanges() { reportRepo.saveFile(); }
    /**
     * Displays the report management menu to the HDB manager.
     * Facilitates interaction between the UI and the underlying report system.
     *
     * @param manager the logged-in HDB manager
     * @return optional return string to indicate command/action
     */
    public String showManagerMenu(HdbManager manager) { return reportUI.showManagerMenu(manager); }
}
