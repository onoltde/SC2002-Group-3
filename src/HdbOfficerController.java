import java.util.Scanner;

public class HdbOfficerController implements UserController{
    private static Scanner sc;
    //Dependencies
    private static HdbOfficer currentUser = null;
    private static HdbOfficerRepo repo;
    private static HdbOfficerUI UI;

    public HdbOfficerController(Scanner scanner) {
        sc = scanner;
        repo = new HdbOfficerRepo();
        UI = new HdbOfficerUI(sc,this, repo);
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