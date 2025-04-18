package Report;

public class ReportController {
    private ReportRepo reportRepo;
    private ReportUI reportUI;

    public ReportController() {
        reportRepo = new ReportRepo();
        reportUI = new ReportUI(this);
    }

    public ReportRepo getRepo() { return reportRepo; }
}
