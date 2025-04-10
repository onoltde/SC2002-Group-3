import java.util.Scanner;

public class HdbOfficerController implements UserController{
    //Dependencies
    private static HdbOfficer currentUser = null;
    private static HdbOfficerRepo repo;
    private static ResidentialApplicationRepo resAppRepo;
    private static TeamApplicationRepo teamAppRepo;
    private static HdbOfficerUI UI;

    public HdbOfficerController() {
        resAppRepo = new ResidentialApplicationRepo();
        teamAppRepo = new TeamApplicationRepo();
        repo = new HdbOfficerRepo(resAppRepo,teamAppRepo);
        UI = new HdbOfficerUI(this, repo);
    }

    public void runPortal() {
        //welcome menu display
        currentUser = UI.displayLogin();
        if (currentUser == null) {
            return;
        }         //if returns null == user exits program
        UI.displayDashboard(currentUser);

    }

    public void exitPortal() {
        repo.saveFile();
    }

    public HdbOfficerRepo getRepo(){
        return repo;
    }

}//end of class