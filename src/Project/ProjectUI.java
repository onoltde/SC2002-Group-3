package Project;
import java.time.LocalDate;
import java.util.*;
import Utility.*;
import HdbManager.*;
import HdbOfficer.*;
import Applicant.*;

public class ProjectUI {

    private static ProjectController controller;

    public ProjectUI(ProjectController projectController){
        controller = projectController;
    }

    // display project detail methods
    public void displayEssentialProjectDetails(Project project){
        String name = project.getName();
        String neighbourhood = project.getNeighbourhood();
        LocalDate openDate = project.getOpenDate();
        LocalDate closeDate = project.getCloseDate();
        InputUtils.printSmallDivider();
        System.out.println("Name: " + name);
        System.out.println("Neighbourhood: " + neighbourhood);
        System.out.println("Applications open from: " + TimeUtils.dateToString(openDate) + " to " + TimeUtils.dateToString(closeDate));

    }

    public void displayFlatDetails(Flat flat){
        InputUtils.printSmallDivider();
        int totalUnits = flat.getTotalUnits();
        int availableUnits = flat.getAvailableUnits();
        int price = flat.getSellingPrice();
        System.out.println(flat.typeToString() + "-Room units left: " + availableUnits + "/" + totalUnits);
        System.out.println(flat.typeToString() + "-Room selling price: $" + price);
        InputUtils.printSmallDivider();
    }

    public void displayProjectAdminDetails(Project project, HashMap<String, HdbManager> managerMap, HashMap<String, HdbOfficer> officerMap) {
        String managerId = project.getManagerId();
        String managerName = managerMap.get(managerId).getName();
        ArrayList<String> assignedOfficers = project.getAssignedOfficers();
        ArrayList<String> pendingOfficers = project.getPendingOfficers();

        InputUtils.printSmallDivider();
        System.out.println("Manager-in-Charge: " + managerName + " (" + managerId + ")");
        System.out.println("---------------------------------------------");
        // Print assigned officers
        System.out.print("Assigned Officers: ");
        if (assignedOfficers.isEmpty()) {
            System.out.println("None");
        } else {
            System.out.println();
            for (String officerId : assignedOfficers) {
                HdbOfficer officer = officerMap.get(officerId);
                System.out.println("  - " + officer.getName() + " (" + officerId + ")");
            }
        }
        System.out.println("---------------------------------------------");
        // Print pending officers
        System.out.print("Pending Officers: ");
        if (pendingOfficers.isEmpty()) {
            System.out.println("None");
        } else {
            System.out.println();
            for (String officerId : pendingOfficers) {
                HdbOfficer officer = officerMap.get(officerId);
                System.out.println("  - " + officer.getName() + " (" + officerId + ")");
            }
        }
        InputUtils.printSmallDivider();
    }

    //applicant methods
    public void displayProjectDashboard(Applicant applicant){

            while (true) {
                InputUtils.printSmallDivider();
                System.out.println("Applicant Project Dashboard");
                System.out.println("----------------------------");
                System.out.println("Please choose an option:");
                System.out.println("1. View current 2-Room projects");
                System.out.println("2. View current 3-Room projects");
                System.out.println("3. Back to menu");
                System.out.print("Enter your choice (1-3): ");

                int choice = InputUtils.readInt();

                switch (choice) {
                    case 1 -> {//show available two room
                        displayTwoRoom(applicant);
                    }
                    case 2 ->{//show available three room
                        displayThreeRoom(applicant);
                    }
                    case 3 -> {//exit
                        controller.saveChanges();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please enter 1-4.\n");
                }

            }
    }

    private void displayTwoRoom(Applicant applicant){
        if (!applicant.canApply(Flat.Type.TWOROOM)){
            InputUtils.printSmallDivider();
            System.out.println("You are not eligible to apply for 2-Room flats.");
            return;
        }

        ArrayList<Project> filteredList = controller.getRepo().filterByAvailUnitType(Flat.Type.TWOROOM);
        if (filteredList.size() == 0){
            System.out.println("No 2-Room projects to display.");
            return;
        }
        int currentIndex = 0;

        while (currentIndex < filteredList.size()) {
            Project currentProject = filteredList.get(currentIndex);

            // Display project counter (x/n)
            InputUtils.printSmallDivider();
            System.out.printf("2-Room Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            // Display project details
            displayEssentialProjectDetails(currentProject);
            displayFlatDetails(currentProject.getFlatInfo().get(Flat.Type.TWOROOM));

            // Show menu options
            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Apply for this project");
            System.out.println("2. Next project");
            System.out.println("3. Exit to menu");
            System.out.println("Enter your choice (1-3): ");


            int choice = InputUtils.readInt();

            switch (choice) {
                case 1: // Apply for current project
                    if (applicant.hasResidentialApplication()){
                        System.out.println("You have an active application. You cannot apply to more than 1 project.");
                        return;
                    }else{
                        controller.applyProject(applicant, currentProject, Flat.Type.TWOROOM);
                        System.out.println("Successfully applied for this project.");
                    }

                case 2: // Next project
                    currentIndex++;
                    break;

                case 3: //exit to menu
                    return;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
    }

    private void displayThreeRoom(Applicant applicant){
        if (!applicant.canApply(Flat.Type.THREEROOM)){
            InputUtils.printSmallDivider();
            System.out.println("You are not eligible to apply for 3-Room flats.");
            return;
        }

        ArrayList<Project> filteredList = controller.getRepo().filterByAvailUnitType(Flat.Type.THREEROOM);
        if (filteredList.size() == 0){
            System.out.println("No 3-Room projects to display.");
            return;
        }
        int currentIndex = 0;

        while (currentIndex < filteredList.size()) {
            Project currentProject = filteredList.get(currentIndex);

            // Display project counter (x/n)
            InputUtils.printSmallDivider();
            System.out.printf("3-Room Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            // Display project details
            displayEssentialProjectDetails(currentProject);
            displayFlatDetails(currentProject.getFlatInfo().get(Flat.Type.THREEROOM));

            // Show menu options
            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Apply for this project");
            System.out.println("2. Next project");
            System.out.println("3. Exit to menu");
            System.out.println("Enter your choice (1-3): ");


            int choice = InputUtils.readInt();

            switch (choice) {
                case 1: // Apply for current project
                    if (applicant.hasResidentialApplication()){
                        System.out.println("You have an active application. You cannot apply to more than 1 project.");
                        return;
                    }else {
                        controller.applyProject(applicant, currentProject, Flat.Type.THREEROOM);
                        System.out.println("Successfully applied for this project.");
                    }
                case 2: // Next project
                    currentIndex++;
                    break;

                case 3: //exit to menu
                    return;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
    }


    //officer methods
    public void displayTwoRoomResProjectsToApply(HdbOfficer officer){
        if (!officer.canApply(Flat.Type.TWOROOM)){
            InputUtils.printSmallDivider();
            System.out.println("You are not eligible to apply for 2-Room flats.");
            return;
        }

        ArrayList<Project> filteredList = controller.getRepo().filterForResApplication(officer.getBlacklist(), Flat.Type.TWOROOM);
        int currentIndex = 0;

        while (currentIndex < filteredList.size()) {
            Project currentProject = filteredList.get(currentIndex);

            // Display project counter (x/n)
            InputUtils.printSmallDivider();
            System.out.printf("2-Room Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            // Display project details
            displayEssentialProjectDetails(currentProject);
            displayFlatDetails(currentProject.getFlatInfo().get(Flat.Type.TWOROOM));

            // Show menu options
            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Apply for this project");
            System.out.println("2. Next project");
            System.out.println("3. Exit to menu");
            System.out.println("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1: // Apply for current project
                    System.out.println("call application menu method applyProject(Officer,currentProject)");

                case 2: // Next project
                    currentIndex++;
                    break;

                case 3: //exit to menu
                    return;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
    }

    public void displayThreeRoomResProjectsToApply(HdbOfficer officer){
        if (!officer.canApply(Flat.Type.THREEROOM)){
            InputUtils.printSmallDivider();
            System.out.println("You are not eligible to apply for 3-Room flats.");
            return;
        }

        ArrayList<Project> filteredList = controller.getRepo().filterForResApplication(officer.getBlacklist(), Flat.Type.THREEROOM);
        int currentIndex = 0;

        while (currentIndex < filteredList.size()) {
            Project currentProject = filteredList.get(currentIndex);

            // Display project counter (x/n)
            InputUtils.printSmallDivider();
            System.out.printf("3-Room Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            // Display project details
            displayEssentialProjectDetails(currentProject);
            displayFlatDetails(currentProject.getFlatInfo().get(Flat.Type.THREEROOM));

            // Show menu options
            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Apply for this project");
            System.out.println("2. Next project");
            System.out.println("3. Exit to menu");
            System.out.println("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1: // Apply for current project
                    System.out.println("call application menu method applyProject(Officer,currentProject)");

                case 2: // Next project
                    currentIndex++;
                    break;

                case 3: //exit to menu
                    return;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
    }

    public String displayTeamProjectsToApply(HdbOfficer officer){
        ArrayList<Project> filteredList = controller.getRepo().filterForTeamApplication(officer);
        if (filteredList.size() == 0){
            System.out.println("No projects to display.");
            return null;
        }
        int currentIndex = 0;

        while (currentIndex < filteredList.size()) {
            Project currentProject = filteredList.get(currentIndex);

            // Display project counter (x/n)
            InputUtils.printSmallDivider();
            System.out.printf("Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            // Display admin project details
            controller.displayAdminProjectDetails(currentProject.getName());

            // Show menu options
            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Apply for this team");
            System.out.println("2. Next project");
            System.out.println("3. Exit to menu");
            System.out.println("Enter your choice (1-3): ");


            int choice = InputUtils.readInt();

            switch (choice) {
                case 1: // Apply for current team
                    return currentProject.getName();

                case 2: // Next project
                    currentIndex++;
                    break;

                case 3: //exit to menu
                    return null;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
        return null;
    }



    // manager methods
    public void displayProjectDashboard(HdbManager applicant){

    }


}//end of class
