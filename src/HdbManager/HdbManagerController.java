package HdbManager;
import Applicant.*;
import Application.Application;
import Application.Residential.ResidentialApplication;
import Application.Residential.ResidentialApplicationController;
import Application.Team.TeamApplication;
import Application.Team.TeamApplicationController;
import Enquiry.EnquiryController;
import HdbOfficer.HdbOfficer;
import HdbOfficer.HdbOfficerController;
import Project.Flat;
import Project.Project;
import Project.ProjectControllerInterface;
import Report.ReportController;
import Users.*;
import Utility.InputUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * HdbManagerController handles the operations performed by the HdbManager.
 * This includes managing projects, processing applications, handling enquiries, generating reports,
 * and interacting with other controllers like HdbOfficer, Applicant, etc.
 */
public class HdbManagerController implements UserController {

    // Dependencies
    private final HdbManagerRepo managerRepo;
    private final HdbManagerUI managerUI;

    // Controller dependencies
    private final ProjectControllerInterface projectController;
    private final HdbOfficerController officerController;
    private final ApplicantController applicantController;
    private final ResidentialApplicationController resAppController;
    private final TeamApplicationController teamAppController;
    private final EnquiryController enquiryController;
    private final ReportController reportController;

    /**
     * Constructor to initialize the HdbManagerController with the necessary controllers.
     *
     * @param projectController       The controller for managing projects.
     * @param officerController       The controller for managing officers.
     * @param applicantController     The controller for managing applicants.
     * @param resAppController        The controller for residential applications.
     * @param teamAppController       The controller for team applications.
     * @param enquiryController       The controller for managing enquiries.
     * @param reportController        The controller for managing reports.
     */
    public HdbManagerController(ProjectControllerInterface projectController,
                                HdbOfficerController officerController,
                                ApplicantController applicantController,
                                ResidentialApplicationController resAppController,
                                TeamApplicationController teamAppController,
                                EnquiryController enquiryController,
                                ReportController reportController) {
        this.projectController = projectController;
        this.officerController = officerController;
        this.applicantController = applicantController;
        this.teamAppController = teamAppController;
        this.resAppController = resAppController;
        this.enquiryController = enquiryController;
        this.reportController = reportController;

        managerRepo = new HdbManagerRepo(this.projectController);
        managerUI = new HdbManagerUI(this);
    }

    /**
     * Displays the login menu for the manager and then navigates to the dashboard if login is successful.
     */
    public void runPortal() {
        // Welcome menu display
        HdbManager currentUser = managerUI.displayLogin(managerRepo);
        if (currentUser == null) {
            return;
        } // if returns null == user exits program
        managerUI.displayDashboard(currentUser);
    }

    /**
     * Displays the enquiry menu for the given manager.
     *
     * @param manager The HdbManager instance.
     */
    public void displayEnquiryMenu(HdbManager manager) {
        enquiryController.showManagerMenu(manager);
        enquiryController.saveChanges();
    }

    /**
     * Displays the report menu and allows the manager to generate reports.
     *
     * @param manager The HdbManager instance.
     */
    public void displayReportMenu(HdbManager manager) {
        while (true) {
            String res = reportController.showManagerMenu(manager);
            if (res == null) continue;
            if ("a".equals(res)) break;
            generateReport(res);
        }
        reportController.saveChanges();
    }

    /**
     * Displays the project management menu and allows the manager to create, edit, or delete projects.
     *
     * @param manager The HdbManager instance.
     */
    public void displayProjectMenu(HdbManager manager) {
        while (true) {
            ArrayList<Object> res = projectController.displayProjectDashboard(manager);
            if (res == null) continue;
            if ("a".equals(res.get(0))) {
                createProject((String) res.get(1), (String) res.get(2), (HashMap<Flat.Type, Flat>) res.get(3),
                        (LocalDate) res.get(4), (LocalDate) res.get(5), manager.getId(),
                        (int) res.get(6), (boolean) res.get(7));
            } else if ("b".equals(res.get(0))) {
                editProject(manager.getId(), (String) res.get(1), (int) res.get(2), (boolean) res.get(3));
            } else if ("c".equals(res.get(0))) {
                deleteProject(manager.getId(), (String) res.get(1));
            } else {
                break;
            }
        }
        projectController.saveChanges();
    }

    /**
     * Displays the residential application menu and allows the manager to process residential applications.
     *
     * @param manager The HdbManager instance.
     */
    public void displayResApplicationMenu(HdbManager manager) {
        while (true) {
            ArrayList<String> res = resAppController.displayApplicationMenu(manager);
            if (res == null) continue;
            if ("c".equals(res.get(0))) break;
            if ("a".equals(res.get(0))) {
                processApplicantBTOApplication(manager.getId(), res.get(1), "true".equals(res.get(2)));
            } else {
                approveApplicantWithdrawal(manager.getId(), res.get(1));
            }
        }
    }

    /**
     * Displays the team application menu and allows the manager to process team applications.
     *
     * @param manager The HdbManager instance.
     */
    public void displayTeamApplicationMenu(HdbManager manager) {
        while (true) {
            ArrayList<String> res = teamAppController.displayApplicationMenu(manager);
            if (res == null) continue;
            if ("c".equals(res.get(0))) break;
            if ("a".equals(res.get(0))) {
                processOfficerApplication(manager.getId(), res.get(1), "true".equals(res.get(2)));
            } else {
                approveOfficerWithdrawal(manager.getId(), res.get(1));
            }
        }
    }

    /**
     * Saves the current state of the manager repository to the file.
     */
    public void saveFile() {
        managerRepo.saveFile();
    }

    /**
     * Retrieves the manager repository for the controller.
     *
     * @return The HdbManagerRepo instance.
     */
    public HdbManagerRepo getRepo() {
        return managerRepo;
    }

    /**
     * Checks if the provided manager is null.
     *
     * @param manager The HdbManager instance.
     * @return True if the manager is null, otherwise false.
     */
    private boolean check(HdbManager manager) {
        if (manager == null) {
            System.out.println("No such manager!");
            return true;
        }
        return false;
    }
    // report
    /**
     * Generates a report for the given applicant ID if the application meets the required criteria.
     * The application must have a "BOOKED" status, and the applicant must exist.
     *
     * @param applicantId The ID of the applicant whose report is being generated.
     */
    public void generateReport(String applicantId) {
        ResidentialApplication application = resAppController.getRepo().getApplications().get(applicantId);
        if (application == null) {
            System.out.println("No such application!");
            return;
        }
        if (application.getStatus() != Application.Status.BOOKED) {
            System.out.println("Applicant must have booked a flat!");
            return;
        }
        Applicant applicant = applicantController.getApplicantRepo().getUser(applicantId);
        if (applicant == null) {
            System.out.println("No such applicant!");
            return;
        }
        System.out.println("Generated Successfully!");
        System.out.println("Report ID: " + reportController.getRepo().addReport(application.getProjectName(),
                applicantId, application.getFlatType(),
                applicant.getAge(), applicant.getMaritalStatus()));
    }

// project managing
    /**
     * Creates a new project with the specified details. The project is only created if the following
     * conditions are met: there are enough units available for the flats, the officer slots are within
     * valid limits, and the open date is before the close date.
     *
     * @param name          The name of the project.
     * @param neighbourhood The neighbourhood where the project is located.
     * @param flatInfo      The flat information including types and available units.
     * @param openDate      The open date for the project.
     * @param closeDate     The close date for the project.
     * @param managerId     The ID of the manager creating the project.
     * @param officerSlots  The number of officer slots available for the project.
     * @param visibility    The visibility status of the project (true for visible, false for hidden).
     */
    public void createProject(String name, String neighbourhood, HashMap<Flat.Type, Flat> flatInfo, LocalDate openDate,
                              LocalDate closeDate, String managerId, int officerSlots, boolean visibility) {
        HdbManager manager = managerRepo.getUser(managerId);
        if (check(manager)) return;
        for (Flat e : flatInfo.values()) {
            if (e.getTotalUnits() < e.getUnitsBooked()) {
                System.out.println("The number of units booked can not exceed the total unit (" +
                        e.getFlatType().toString() + ")!");
                return;
            }
        }
        if (officerSlots < 0) {
            System.out.println("The number of officer slots can not be negative!");
            return;
        }
        if (officerSlots > 10) {
            System.out.println("The number of officer slots can not exceed 10!");
            return;
        }
        if (openDate.isAfter(closeDate)) {
            System.out.println("The open date must be before close date!");
            return;
        }
        Project project = new Project(name, neighbourhood, flatInfo, openDate, closeDate,
                managerId, officerSlots, new ArrayList<String>(), visibility, new ArrayList<String>());
        if (manager.canManage(project)) {
            manager.setManagedProject(project);
            projectController.getRepo().addProject(project);

            System.out.println();
            InputUtils.printSmallDivider();
            System.out.println("Successfully created the project!");
            System.out.println();
        } else {
            System.out.println("The manager can not manage the project at the time!");
        }
    }

    /**
     * Edits an existing project by updating its officer slots and visibility.
     * The manager must be managing the project for the edit to be allowed.
     *
     * @param managerId    The ID of the manager editing the project.
     * @param name         The name of the project to edit.
     * @param officerSlots The new number of officer slots for the project.
     * @param visibility   The new visibility status of the project (true for visible, false for hidden).
     */
    public void editProject(String managerId, String name, int officerSlots, boolean visibility) {
        HdbManager manager = managerRepo.getUser(managerId);
        if (check(manager)) return;
        Project project = projectController.getRepo().getProject(name);
        if (manager.getManagedProject() == null || !name.equals(manager.getManagedProjectName())) {
            System.out.println("The manager is not managing the project!");
            return;
        }
        project.setOfficerSlots(officerSlots);
        if (project.isVisible() != visibility) project.toggleVisibility();

        System.out.println();
        InputUtils.printSmallDivider();
        System.out.println("Successfully edited the project!");
        System.out.println();
    }

    /**
     * Deletes an existing project that the manager is managing.
     * The manager must be managing the project for the delete operation to be allowed.
     *
     * @param managerId The ID of the manager deleting the project.
     * @param name      The name of the project to delete.
     */
    public void deleteProject(String managerId, String name) {
        HdbManager manager = managerRepo.getUser(managerId);
        if (check(manager)) return;
        if (manager.getManagedProject() == null || !name.equals(manager.getManagedProjectName())) {
            System.out.println("The manager is not managing the project!");
            return;
        }
        manager.setManagedProject(null);
        projectController.getRepo().deleteProject(name);

        System.out.println();
        InputUtils.printSmallDivider();
        System.out.println("Successfully deleted the project!");
        System.out.println();
    }

    // officer application processing
    /**
     * Processes the officer's application for the given manager.
     * It checks if the officer's application exists, whether the manager is managing the relevant project,
     * and if the application has been previously approved or rejected.
     * The status parameter indicates whether to approve (true) or reject (false) the application.
     *
     * @param managerId The ID of the manager processing the application.
     * @param officerId The ID of the officer whose application is being processed.
     * @param status    The status of the application (true for approval, false for rejection).
     * @return true if the process was successful, false otherwise.
     */
    public boolean processOfficerApplication(String managerId, String officerId, boolean status) {
        HdbManager manager = managerRepo.getUser(managerId);
        if (check(manager)) return false;

        ////////////////////////////////////////////////////////
        HdbOfficer officer = officerController.getRepo().getUser(officerId);
        if (officer == null) {
            System.out.println("No such officer!");
            return false;
        }
        if (!officer.hasTeamApplication()) {
            System.out.println("The officer has no application at the moment!");
            return false;
        }

        /////////////////////////////////////////////////////////
        TeamApplication application = officer.getTeamApplication();
        if (application == null) {
            System.out.println("No such application!");
            return false;
        }
        if (!application.getProjectName().equals(manager.getManagedProject().getName())) {
            System.out.println("The manager is not managing the project!");
            return false;
        }
        if (application.getStatus() == Application.Status.SUCCESSFUL) {
            System.out.println("The officer is already approved!");
            return false;
        }
        if (application.getStatus() == Application.Status.UNSUCCESSFUL) {
            System.out.println("The officer is already rejected!");
            return false;
        }
        ///////////////////////////////////////////////////////////
        if (!status) {
            System.out.println("Successfully rejected!");
            application.updateStatus(Application.Status.UNSUCCESSFUL);
            return true;
        }

        ///////////////////////////////////////////////////////////
        Project project = projectController.getRepo().getProject(application.getProjectName());

        if (project.getOfficerSlots() == 0) {
            System.out.println("There is no slots left!");
            return false;
        }
        if (status) {
            System.out.println("Successfully approved!");
            officer.assignProject(application.getProjectName());
            application.updateStatus(Application.Status.SUCCESSFUL);
            project.setOfficerSlots(project.getOfficerSlots() - 1);
        } else {
            System.out.println("Successfully rejected!");
            officer.assignProject(application.getProjectName());
            application.updateStatus(Application.Status.UNSUCCESSFUL);
        }
        officerController.getRepo().saveFile();
        return true;
    }

// applicant BTO application processing
    /**
     * Processes the applicant's BTO application for the given manager.
     * It checks if the applicant's application exists, whether the manager is managing the relevant project,
     * and if the application has been previously approved or rejected.
     * The status parameter indicates whether to approve (true) or reject (false) the application.
     *
     * @param managerId  The ID of the manager processing the application.
     * @param applicantId The ID of the applicant whose application is being processed.
     * @param status     The status of the application (true for approval, false for rejection).
     * @return true if the process was successful, false otherwise.
     */
    public boolean processApplicantBTOApplication(String managerId, String applicantId, boolean status) {
        HdbManager manager = managerRepo.getUser(managerId);
        if (check(manager)) return false;

        ////////////////////////////////////////////////////////
        ResidentialApplication application = resAppController.getRepo().getApplications().get(applicantId);
        if (application == null) {
            System.out.println("No such application!");
            return false;
        }

        /////////////////////////////////////////////////////////
        if (!application.getProjectName().equals(manager.getManagedProject().getName())) {
            System.out.println("The manager is not managing the project!");
            return false;
        }
        if (application.getStatus() == Application.Status.SUCCESSFUL) {
            System.out.println("The applicant is already approved!");
            return false;
        }
        if (application.getStatus() == Application.Status.UNSUCCESSFUL) {
            System.out.println("The applicant is already rejected!");
            return false;
        }
        ///////////////////////////////////////////////////////////
        if (!status) {
            System.out.println("Successfully rejected!");
            application.updateStatus(Application.Status.UNSUCCESSFUL);
            return true;
        }

        ///////////////////////////////////////////////////////////
        if (status) {
            System.out.println("Successfully approved!");
            application.updateStatus(Application.Status.SUCCESSFUL);
        } else {
            System.out.println("Successfully rejected!");
            application.updateStatus(Application.Status.UNSUCCESSFUL);
        }
        applicantController.getApplicantRepo().saveFile();
        return true;
    }

// approve applicant withdrawal
    /**
     * Approves the withdrawal request for an applicant, changing the status to WITHDRAWN.
     * It checks if the application exists, whether the manager is managing the relevant project,
     * and if the application is in a valid state for withdrawal.
     *
     * @param managerId  The ID of the manager processing the withdrawal.
     * @param applicantId The ID of the applicant whose withdrawal is being processed.
     * @return true if the process was successful, false otherwise.
     */
    public boolean approveApplicantWithdrawal(String managerId, String applicantId) {
        HdbManager manager = managerRepo.getUser(managerId);
        if (check(manager)) return false;
        ResidentialApplication application = resAppController.getRepo().getApplications().get(applicantId);
        if (application == null) {
            System.out.println("No such application!");
            return false;
        }
        if (!application.getProjectName().equals(manager.getManagedProject().getName())) {
            System.out.println("The manager is not managing the project!");
            return false;
        }

        if (application.getStatus() == Application.Status.WITHDRAWN) {
            System.out.println("The application is already withdrawn!");
            return false;
        }
        if (application.getStatus() != Application.Status.WITHDRAWING) {
            System.out.println("The application must request for withdrawal!");
            return false;
        }
        System.out.println("Successfully approved!");
        application.updateStatus(Application.Status.WITHDRAWN);
        applicantController.getApplicantRepo().saveFile();
        return true;
    }

// approve officer withdrawal
    /**
     * Approves the withdrawal request for an officer, changing the status to WITHDRAWN.
     * It checks if the application exists, whether the manager is managing the relevant project,
     * and if the application is in a valid state for withdrawal.
     *
     * @param managerId  The ID of the manager processing the withdrawal.
     * @param officerId  The ID of the officer whose withdrawal is being processed.
     * @return true if the process was successful, false otherwise.
     */
    public boolean approveOfficerWithdrawal(String managerId, String officerId) {
        HdbManager manager = managerRepo.getUser(managerId);
        if (check(manager)) return false;
        TeamApplication application = teamAppController.getRepo().getApplications().get(officerId);
        if (application == null) {
            System.out.println("No such application!");
            return false;
        }
        if (!application.getProjectName().equals(manager.getManagedProject().getName())) {
            System.out.println("The manager is not managing the project!");
            return false;
        }

        if (application.getStatus() == Application.Status.WITHDRAWN) {
            System.out.println("The application is already withdrawn!");
            return false;
        }
        if (application.getStatus() != Application.Status.WITHDRAWING) {
            System.out.println("The application must request for withdrawal!");
            return false;
        }
        System.out.println("Successfully approved!");
        application.updateStatus(Application.Status.WITHDRAWN);
        officerController.getRepo().saveFile();
        return true;
    }
}