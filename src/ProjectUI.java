import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;


public class ProjectUI {
    private static Scanner sc;
    private static ProjectController controller;

    public ProjectUI(Scanner scanner,ProjectController projectController){
        sc = scanner;
        controller = projectController;
    }
    public void printDivider(){
        System.out.println("===================================================================");
    }

    public void displayProjectDashboard(Applicant applicant){

        while (true) {
            printDivider();
            sc.nextLine();
            System.out.println("assssss");
            System.out.println("Please choose an option:");
            System.out.println("1. View 2-Room projects");
            System.out.println("2. View 3-Room projects");
            System.out.println("3. Exit back to Applicant Portal");
            System.out.print("Enter your choice (1-3): ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> {//show available two room
                        displayFlats(applicant, Flat.Type.TWOROOM);
                    }
                    case 2 ->{//show available three room
                        displayFlats(applicant, Flat.Type.THREEROOM);
                    }
                    case 3 -> {//exit
                        controller.exitMenu();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please enter 1-4.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.\n");
            }
        }
    }

    public boolean displayFlats(Applicant applicant,Flat.Type flatType) {
        if (!applicant.canApplyTwoRoom()) {
            System.out.println("You are not eligible to apply for " + flatType + " flats.");
        } else {
            System.out.println("You can apply for these:");
            HashMap<String, Project> projectMap = controller.filterByTypeAndDate(flatType);
            if (projectMap == null || projectMap.isEmpty()) {
                System.out.println("No projects available.");
                return false;
            }

            List<Project> projects = List.copyOf(projectMap.values());
            List<String> projectNames = List.copyOf(projectMap.keySet());

            while (true) {
                System.out.println("\n===== " + flatType+ " PROJECT LIST =====");
                IntStream.range(0, projects.size())
                        .forEach(i -> System.out.printf("%d. %s%n", i + 1, projectNames.get(i)));
                System.out.printf("%d. Exit%n", projects.size() + 1);
                System.out.print("Select project (1-" + (projects.size() + 1) + "): ");

                try {
                    int choice = Integer.parseInt(sc.nextLine());

                    if (choice == projects.size() + 1) {
                        System.out.println("Exiting project list...");
                        break;
                    } else if (choice > 0 && choice <= projects.size()) {
                        displayFlatMenu(applicant,projects.get(choice - 1),Flat.Type.TWOROOM);
                    } else {
                        System.out.println("Invalid selection! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number!");
                }
            }
        }
        controller.exitMenu();
        return true;
    }

    public void displayFlatMenu(Applicant applicant, Project project,Flat.Type flatType){
        String name = project.getName();
        String neighbourhood = project.getNeighbourhood();
        int availUnits = project.getFlatInfo().get(flatType).getAvailableUnits();
        int toalUnits = project.getFlatInfo().get(flatType).getTotalUnits();
        int price = project.getFlatInfo().get(flatType).getSellingPrice();
        LocalDate openDate = project.getOpenDate();
        LocalDate closeDate = project.getCloseDate();

        System.out.println("Project Name: " + name);
        System.out.println("Neigbourhood: " + neighbourhood);
        System.out.println("Flat Type: " + flatType);
        System.out.println("Number of Available units: " + availUnits + "/" + toalUnits);
        System.out.println("Price of each unit: $" + price);
        System.out.println("Application open date: " + openDate);
        System.out.println("Application close date: " + closeDate);
        System.out.println("------------------------------------------------------------");
        System.out.println("Please select an option:xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    public void displayProjectMenu(HdbManager applicant){

    }

    public void displayProjectMenu(HdbOfficer officer){

    }

    public void displayEssentialProjectDetails(Project project){
        String name = project.getName();
        String neighbourhood = project.getNeighbourhood();
        LocalDate openDate = project.getOpenDate();
        LocalDate closeDate = project.getCloseDate();
        Flat twoRoom = project.getFlatInfo().get(Flat.Type.TWOROOM);
        int totalTwoRoom = twoRoom.getTotalUnits();
        int availTwoRoom = twoRoom.getAvailableUnits();
        int twoRoomPrice = twoRoom.getSellingPrice();
        Flat threeRoom = project.getFlatInfo().get(Flat.Type.THREEROOM);
        int totalThreeRoom = threeRoom.getTotalUnits();
        int availThreeRoom = threeRoom.getAvailableUnits();
        int threeRoomPrice = threeRoom.getSellingPrice();

        System.out.println("Name: " + name);
        System.out.println("Neighbourhood:" + neighbourhood);
        System.out.println("Application from " + openDate + " - " + closeDate);
        System.out.println("---------------------------------------------------");
        System.out.println("3-Room units left: " + availThreeRoom + "/" + totalThreeRoom);
        System.out.println("3-Room selling price: $" + threeRoomPrice);
        System.out.println("2-Room units left: " + availTwoRoom);
        System.out.println("2-Room units selling price: $" + twoRoomPrice);
        System.out.println("---------------------------------------------------");
    }

    public void displayProjectFlatDetails(Project project, Flat.Type flatType){
        Flat flat = project.getFlatInfo().get(flatType);
        int totalUnits = flat.getTotalUnits();
        int availableUnits = flat.getAvailableUnits();
        int price = flat.getSellingPrice();
        int roomCount = (flatType == Flat.Type.THREEROOM) ? 3 : 2;
        System.out.println("---------------------------------------------------");
        System.out.println(roomCount + "-Room units left: " + availableUnits + "/" + totalUnits);
        System.out.println("3-Room selling price: $" + price);
        System.out.println("---------------------------------------------------");
    }

    public void displayProjectAdminDetails(Project project){
        String managerInCharge = project.getManagerId();
        ArrayList<String> assignedOfficers = project.getAssignedOfficers();
        ArrayList<String> pendingOfficers = project.getPendingOfficers();

        System.out.println("---------------------------------------------------");
        System.out.println("");
    }


}//end of class
