package Report;

import HdbManager.HdbManager;

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
}
