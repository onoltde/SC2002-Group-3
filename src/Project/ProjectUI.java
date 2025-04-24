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

/**
 * UI class for interacting with the project system from the console.
 * Allows both HDB officers and applicants to view and apply to projects.
 */
public class ProjectUI {

    private static ProjectController controller;

    /**
     * Constructor for ProjectUI.
     *
     * @param projectController the controller handling project logic
     */
    public ProjectUI(ProjectController projectController){
        controller = projectController;
    }

    /**
     * Displays basic details about a project including name, neighbourhood, and application dates.
     *
     * @param project the project to display
     */
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

    /**
     * Displays details about a specific flat type such as available units and price.
     *
     * @param flat the flat to display
     */
    public void displayFlatDetails(Flat flat){
        int totalUnits = flat.getTotalUnits();
        int availableUnits = flat.getAvailableUnits();
        int price = flat.getSellingPrice();
        InputUtils.printSmallDivider();
        System.out.println(flat.typeToString() + "-Room units left: " + availableUnits + "/" + totalUnits);
        System.out.println(flat.typeToString() + "-Room selling price: $" + price);
    }

    /**
     * Displays admin-level project details including the manager and officer assignments.
     *
     * @param project the project whose admin details are to be displayed
     * @param managerMap a map of manager IDs to manager objects
     * @param officerMap a map of officer IDs to officer objects
     */
    public void displayProjectAdminDetails(Project project, HashMap<String, HdbManager> managerMap, HashMap<String, HdbOfficer> officerMap) {
        String managerId = project.getManagerId();
        String managerName = managerMap.get(managerId).getName();
        ArrayList<String> assignedOfficers = project.getAssignedOfficers();
        ArrayList<String> pendingOfficers = project.getPendingOfficers();

        InputUtils.printSmallDivider();
        System.out.println("Manager-in-Charge: " + managerName + " (" + managerId + ")");
        System.out.println("---------------------------------------------");

        // Assigned Officers
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

        // Pending Officers
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

    /**
     * Displays a dashboard interface for applicants to explore and apply for projects.
     *
     * @param applicant the current applicant
     * @param residentialApplicationController the controller managing residential applications
     */
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
                case 1 -> displayTwoRoom(applicant, residentialApplicationController);
                case 2 -> displayThreeRoom(applicant, residentialApplicationController); // Assuming this exists in your full file
                case 3 -> {
                    controller.saveChanges();
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-4.\n");
            }
        }
    }

    /**
     * Displays 2-Room project options for eligible applicants and lets them apply.
     *
     * @param applicant the current applicant
     * @param residentialApplicationController the controller for application processing
     */
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
            InputUtils.printSmallDivider();
            System.out.printf("2-Room Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            displayEssentialProjectDetails(currentProject);
            displayFlatDetails(currentProject.getFlatInfo().get(Flat.Type.TWOROOM));

            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Apply for this project");
            System.out.println("2. Next project");
            System.out.println("3. Exit to menu");
            System.out.println("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {
                    if (applicant.hasResidentialApplication()){
                        System.out.println("You have an active application. You cannot apply to more than 1 project.");
                        return;
                    } else {
                        residentialApplicationController.applyProject(applicant, currentProject, Flat.Type.TWOROOM);
                        System.out.println("Successfully applied for this project.");
                        return;
                    }
                }
                case 2 -> currentIndex++;
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
    }


    /**
     * Displays 3-Room project options for eligible applicants and lets them apply.
     *
     * @param applicant the current applicant
     * @param residentialApplicationController the controller for application processing
     */
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
            InputUtils.printSmallDivider();
            System.out.printf("3-Room Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            displayEssentialProjectDetails(currentProject);
            displayFlatDetails(currentProject.getFlatInfo().get(Flat.Type.THREEROOM));

            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Apply for this project");
            System.out.println("2. Next project");
            System.out.println("3. Exit to menu");
            System.out.println("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {
                    if (applicant.hasResidentialApplication()){
                        System.out.println("You have an active application. You cannot apply to more than 1 project.");
                        return;
                    } else {
                        residentialApplicationController.applyProject(applicant, currentProject, Flat.Type.THREEROOM);
                        System.out.println("Successfully applied for this project.");
                        return;
                    }
                }
                case 2 -> currentIndex++;
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
    }

    /**
     * Allows an HDB officer to view and apply for available 2-Room residential projects,
     * excluding any blacklisted ones.
     *
     * @param officer the HDB officer attempting to apply
     * @return the name of the project applied for, or null if none
     */
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
            InputUtils.printSmallDivider();
            System.out.printf("2-Room Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            displayEssentialProjectDetails(currentProject);
            displayFlatDetails(currentProject.getFlatInfo().get(Flat.Type.TWOROOM));

            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Apply for this project");
            System.out.println("2. Next project");
            System.out.println("3. Exit to menu");
            System.out.println("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {
                    return currentProject.getName();
                }
                case 2 -> currentIndex++;
                case 3 -> {
                    return null;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
        return null;
    }

    /**
     * Allows an HDB officer to view and apply for available 3-Room residential projects,
     * excluding any blacklisted ones.
     *
     * @param officer the HDB officer attempting to apply
     * @return the name of the project applied for, or null if none
     */
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
            InputUtils.printSmallDivider();
            System.out.printf("3-Room Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            displayEssentialProjectDetails(currentProject);
            displayFlatDetails(currentProject.getFlatInfo().get(Flat.Type.THREEROOM));

            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Apply for this project");
            System.out.println("2. Next project");
            System.out.println("3. Exit to menu");
            System.out.println("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {
                    return currentProject.getName();
                }
                case 2 -> currentIndex++;
                case 3 -> {
                    return null;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
        return null;
    }

    /**
     * Allows an HDB officer to view and apply to available team-based projects.
     *
     * @param officer the HDB officer attempting to apply for a team project
     * @return the name of the project applied for, or null if none
     */
    public String displayTeamProjectsToApply(HdbOfficer officer){
        ArrayList<Project> filteredList = controller.getRepo().filterForTeamApplication(officer);
        if (filteredList.size() == 0){
            System.out.println("No projects to display.");
            return null;
        }

        int currentIndex = 0;
        while (currentIndex < filteredList.size()) {
            Project currentProject = filteredList.get(currentIndex);
            InputUtils.printSmallDivider();
            System.out.printf("Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            controller.displayAdminProjectDetails(currentProject.getName());

            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Apply for this team");
            System.out.println("2. Next project");
            System.out.println("3. Exit to menu");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {
                    return currentProject.getName();
                }
                case 2 -> currentIndex++;
                case 3 -> {
                    return null;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
        return null;
    }

    /**
     * Displays the dashboard menu for HDB managers, allowing them to manage or view projects.
     *
     * @param manager the HDB manager accessing the dashboard
     * @return an ArrayList of objects based on selected action; may return "d" for going back
     */
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
            }
            case 2 -> {
                return editProject(manager);
            }
            case 3 -> {
                return deleteProject(manager);
            }
            case 4 -> {
                if(manager.getManagedProject() == null) {
                    InputUtils.printSmallDivider();
                    System.out.println("The manager does not have project!");
                } else {
                    displayEssentialProjectDetails(manager.getManagedProject());
                }
                return null;
            }
            case 5 -> {
                displayProjects();
                return null;
            }
            case 6 -> {
                return new ArrayList<>(Arrays.asList("d"));
            }
            default -> {
                System.out.println("Invalid option, please try again.");
            }
        }
        return null;
    }

    /**
     * Creates a new project by gathering necessary details from the user such as name, neighborhood,
     * flat details (including total units, booked units, and selling price for each flat type),
     * opening and closing dates, officer slots, and visibility.
     *
     * @param manager the HDB manager creating the project
     * @return a list of project details including project type and various attributes
     */
    public ArrayList<Object> createProject(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter name: ");
        String name = InputUtils.nextLine();
        System.out.print("Enter neighborhood: ");
        String neighborhood = InputUtils.nextLine();

        // Flat type details collection
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

        // Opening and closing date collection
        LocalDate openDate, closeDate;
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

        // Officer slots and visibility collection
        System.out.print("Enter officer slots: ");
        int officerSlots = InputUtils.readInt();
        boolean visibility;
        while (true) {
            System.out.print("Enter visibility (Y/N): ");
            String dummy = InputUtils.nextLine();
            if ("Y".equals(dummy)) {
                visibility = true;
                break;
            } else if ("N".equals(dummy)) {
                visibility = false;
                break;
            } else {
                System.out.println("Please enter valid input!");
            }
        }

        return new ArrayList<>(Arrays.asList("a", name, neighborhood, flatType, openDate, closeDate, officerSlots, visibility));
    }

    /**
     * Edits an existing project by gathering updated details such as project name, officer slots, and visibility.
     *
     * @param manager the HDB manager editing the project
     * @return a list of edited project details
     */
    public ArrayList<Object> editProject(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter name: ");
        String name = InputUtils.nextLine();
        System.out.print("Enter officer slots: ");
        int officerSlots = InputUtils.readInt();

        // Visibility input
        boolean visibility;
        while (true) {
            System.out.print("Enter visibility (Y/N): ");
            String dummy = InputUtils.nextLine();
            if ("Y".equals(dummy)) {
                visibility = true;
                break;
            } else if ("N".equals(dummy)) {
                visibility = false;
                break;
            } else {
                System.out.println("Please enter valid input!");
            }
        }

        return new ArrayList<>(Arrays.asList("b", name, officerSlots, visibility));
    }

    /**
     * Deletes an existing project by requesting the project name.
     *
     * @param manager the HDB manager deleting the project
     * @return a list containing the project deletion action and the project name
     */
    public ArrayList<Object> deleteProject(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter name: ");
        String name = InputUtils.nextLine();
        return new ArrayList<>(Arrays.asList("c", name));
    }

    /**
     * Displays the project selection menu for viewing different types of flats.
     * Offers options for the user to view 2-Room or 3-Room projects or go back.
     */
    public void displayProjects() {
        while (true) {
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

    /**
     * Displays projects based on flat type (2-Room or 3-Room) and provides navigation options.
     *
     * @param flatType the type of flat to display (either 2-Room or 3-Room)
     */
    public void displayProjects(Flat.Type flatType) {
        ArrayList<Project> filteredList = controller.getRepo().filterByVisibility();
        int currentIndex = 0;

        while (currentIndex < filteredList.size()) {
            Project currentProject = filteredList.get(currentIndex);

            InputUtils.printSmallDivider();
            System.out.printf(flatType.toString() + " Project (%d/%d):\n", currentIndex + 1, filteredList.size());

            displayEssentialProjectDetails(currentProject);
            displayFlatDetails(currentProject.getFlatInfo().get(flatType));

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
