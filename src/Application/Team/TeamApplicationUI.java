package Application.Team;
import HdbOfficer.*;
import HdbManager.*;
import Project.ProjectController;
import Utility.*;
public class TeamApplicationUI {

    private TeamApplicationController teamAppController;

    public TeamApplicationUI(TeamApplicationController teamAppController){
        this.teamAppController = teamAppController;
    }

    public void displayApplicationMenu(HdbOfficer officer){
        if (!officer.hasTeamApplication()){
            System.out.println("You do not have an an active Team application.");
            System.out.println("To make an application, please go to \'Apply for Team\'.");
        }else{
            TeamApplication application = officer.getTeamApplication();
            String projectName = application.getProjectName();
            System.out.println("Applied to : " + projectName + " Team");
            System.out.println("Status : " + application.getStatus());
        }

        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Please choose an option:");
            System.out.println("1. View applied Team details");
            System.out.println("2. Apply for Team");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {// view applied project details
                    if (!officer.hasTeamApplication()){
                        System.out.println("You do not have an an active Team application.");
                        System.out.println("To make an application, please go to \'Apply for Team\'.");
                    }else{
                        teamAppController.displayAssignedProject(officer);
                    }
                }
                case 2 -> {//apply for projects
                    teamAppController.displayProjects(officer);
                }
                case 3 -> {//exit
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }
        }
    }


    public void displayApplicationMenu(HdbManager manager){

    }
}
