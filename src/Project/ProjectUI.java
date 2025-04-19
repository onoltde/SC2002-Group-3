package Project;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

import Application.Residential.ResidentialApplicationController;
import Utility.*;
import HdbManager.*;
import HdbOfficer.*;
import Applicant.*;
import java.time.format.DateTimeParseException;


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
        int totalUnits = flat.getTotalUnits();
        int availableUnits = flat.getAvailableUnits();
        int price = flat.getSellingPrice();
        InputUtils.printSmallDivider();
        System.out.println(flat.typeToString() + "-Room units left: " + availableUnits + "/" + totalUnits);
        System.out.println(flat.typeToString() + "-Room selling price: $" + price);
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
    }

    //applicant methods
    public void displayProjectDashboard(Applicant applicant, ResidentialApplicationController residentialApplicationController){

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
                        displayTwoRoom(applicant,residentialApplicationController);
                    }
                    case 2 ->{//show available three room
                        displayThreeRoom(applicant,residentialApplicationController);
                    }
                    case 3 -> {//exit
                        controller.saveChanges();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please enter 1-4.\n");
                }

            }
    }

    private void displayTwoRoom(Applicant applicant, ResidentialApplicationController residentialApplicationController){
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
                        residentialApplicationController.applyProject(applicant,currentProject, Flat.Type.TWOROOM);
                        System.out.println("Successfully applied for this project.");
                        return;
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

    private void displayThreeRoom(Applicant applicant, ResidentialApplicationController residentialApplicationController){
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
                        residentialApplicationController.applyProject(applicant,currentProject, Flat.Type.THREEROOM);
                        System.out.println("Successfully applied for this project.");
                        return;
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
    public String displayTwoRoomResProjectsToApply(HdbOfficer officer){
        if (!officer.canApply(Flat.Type.TWOROOM)){
            InputUtils.printSmallDivider();
            System.out.println("You are not eligible to apply for 2-Room flats.");
            return null;
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

    public String displayThreeRoomResProjectsToApply(HdbOfficer officer){
        if (!officer.canApply(Flat.Type.THREEROOM)){
            InputUtils.printSmallDivider();
            System.out.println("You are not eligible to apply for 3-Room flats.");
            return null;
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
            System.out.print("Enter your choice (1-3): ");


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
    public ArrayList<Object> displayProjectDashboard(HdbManager manager){
        System.out.println("-------------------------------");
        System.out.println("\n Please choose an option:");
        System.out.println("-------------------------------");
        System.out.println("1. Create project");
        System.out.println("2. Edit project");
        System.out.println("3. Delete project");
        System.out.println("4. View my project");
        System.out.println("5. View all projects");
        System.out.println("6. Back");
        System.out.print("Enter your choice (1-6): ");
        int choice = InputUtils.readInt();
        switch (choice) {
            case 1 -> {
                return createProject(manager);
            } case 2 -> {
                return editProject(manager);
            } case 3 -> {
                return deleteProject(manager);
            } case 4 -> {
                if(manager.getManagedProject() == null) {
                    InputUtils.printSmallDivider();
                    System.out.println("The manager does not have project!");
                } else {
                    displayEssentialProjectDetails(manager.getManagedProject());
                }
                return null;
            } case 5 -> {
                displayProjects();
                return null;
            } case 6 -> {
                return new ArrayList<>(Arrays.asList("d"));
            } default -> {
                System.out.println("Invalid option, please try again.");
            }
        }
        return null;
    }
    public ArrayList<Object> createProject(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter name: ");
        String name = InputUtils.nextLine();
        System.out.print("Enter neighborhood: ");
        String neighborhood = InputUtils.nextLine();
        /////////// flatType
        HashMap<Flat.Type, Flat> flatType = new HashMap<>();
        for (Flat.Type type : Flat.Type.values()) {
            System.out.println("Enter details for " + type + ":");

            System.out.print("Total units: ");
            int totalUnits = InputUtils.readInt();

            System.out.print("Booked units: ");
            int bookedUnits = InputUtils.readInt();

            System.out.print("Selling price: ");
            int sellingPrice = InputUtils.readInt();

            Flat flat;
            if (type == Flat.Type.TWOROOM) {
                flat = new TwoRoomFlat(type, totalUnits, bookedUnits, sellingPrice);
            } else {
                flat = new ThreeRoomFlat(type, totalUnits, bookedUnits, sellingPrice);
            }

            flatType.put(type, flat);
        }
        ////////////////
        LocalDate openDate, closeDate;
        ////// open date
        while (true) {
            System.out.print("Enter Opening Date (YYYY-MM-DD): ");
            String e = InputUtils.nextLine();
            try {
                openDate = LocalDate.parse(e);
                break;
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        ///// close date
        while (true) {
            System.out.print("Enter Closing Date (YYYY-MM-DD): ");
            String e = InputUtils.nextLine();
            try {
                closeDate = LocalDate.parse(e);
                break;
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        /////
        System.out.print("Enter officer slots: ");
        int officerSlots = InputUtils.readInt();
        boolean visibility;
        while(true) {
            System.out.print("Enter visibility (Y/N): ");
            String dummy = InputUtils.nextLine();
            if(dummy.compareTo("Y") == 0) {
                visibility = true;
                break;
            } else if(dummy.compareTo("N") == 0) {
                visibility = false;
                break;
            } else {
                System.out.println("Please enter valid input!");
            }
        }
        return new ArrayList<>(Arrays.asList(   "a", name, neighborhood, flatType, openDate,
                                                closeDate, officerSlots, visibility));
    }
    public ArrayList<Object> editProject(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter name: ");
        String name = InputUtils.nextLine();
        System.out.print("Enter officer slots: ");
        int officerSlots = InputUtils.readInt();
        boolean visibility;
        while(true) {
            System.out.print("Enter visibility (Y/N): ");
            String dummy = InputUtils.nextLine();
            if(dummy.compareTo("Y") == 0) {
                visibility = true;
                break;
            } else if(dummy.compareTo("N") == 0) {
                visibility = false;
                break;
            } else {
                System.out.println("Please enter valid input!");
            }
        }
        return new ArrayList<>(Arrays.asList("b", name, officerSlots, visibility));
    }
    public ArrayList<Object> deleteProject(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter name: ");
        String name = InputUtils.nextLine();
        return new ArrayList<>(Arrays.asList("c", name));
    }

    public void displayProjects() {
        while (true) {
            // Show menu options
            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. View 2-Room projects");
            System.out.println("2. View 3-Room projects");
            System.out.println("3. Back");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1:
                    displayProjects(Flat.Type.TWOROOM);
                    break;
                case 2:
                    displayProjects(Flat.Type.THREEROOM);
                    break;

                case 3: //exit to menu
                    return;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    public void displayProjects(Flat.Type flatType) {

        ArrayList<Project> filteredList = controller.getRepo().filterByVisibility();
        int currentIndex = 0;

        while (currentIndex < filteredList.size()) {
            Project currentProject = filteredList.get(currentIndex);

            // Display project counter (x/n)
            InputUtils.printSmallDivider();
            System.out.printf(flatType.toString() + " Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            // Display project details
            displayEssentialProjectDetails(currentProject);
            displayFlatDetails(currentProject.getFlatInfo().get(flatType));

            // Show menu options
            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Next project");
            System.out.println("2. Previous project");
            System.out.println("3. Exit to menu");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1:
                    currentIndex++;
                    break;
                case 2:
                    currentIndex = (currentIndex == 0 ? 0 : currentIndex - 1);
                    break;

                case 3: //exit to menu
                    return;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
    }

}//end of class
