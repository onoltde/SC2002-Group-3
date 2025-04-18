package HdbManager;
import Applicant.*;
import Application.Application;
import Application.Residential.ResidentialApplication;
import Application.Residential.ResidentialApplicationController;
import Application.Team.TeamApplication;
import Application.Team.TeamApplicationController;
import Enquiry.Enquiry;
import Enquiry.EnquiryController;
import HdbOfficer.HdbOfficer;
import HdbOfficer.HdbOfficerController;
import Project.Flat;
import Project.Project;
import Project.ProjectControllerInterface;
import Report.ReportController;
import Users.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class HdbManagerController implements UserController{
    //Dependencies
    private final HdbManagerRepo managerRepo;
    private final HdbManagerUI managerUI;
    //controller dependencies
    private final ProjectControllerInterface projectController;
    private final HdbOfficerController officerController;
    private final ApplicantController applicantController;
    private final ResidentialApplicationController resAppController;
    private final TeamApplicationController teamAppController;
    private final EnquiryController enquiryController;
    private final ReportController reportController;

    //constructor
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

    public void runPortal() {
        //welcome menu display
        HdbManager currentUser = managerUI.displayLogin(managerRepo);
        if (currentUser == null) {
            return;
        }         //if returns null == user exits program
        managerUI.displayDashboard(currentUser);

    }

    public void displayProjectMenu(HdbManager manager){
        projectController.displayProjectDashboard(manager);
    }
    public void displayEnquiryMenu(HdbManager manager) { enquiryController.showManagerMenu(manager);}
    public void displayResApplicationMenu(HdbManager manager) { resAppController.displayApplicationMenu(manager); }
    public void displayTeamApplicationMenu(HdbManager manager) { teamAppController.displayApplicationMenu(manager);}
    public void displayReportMenu(HdbManager manager) { reportController.showManagerMenu(manager); }

    public void saveFile() {
        managerRepo.saveFile();
    }

    public HdbManagerRepo getRepo(){
        return managerRepo;
    }

    private boolean check(HdbManager manager) {
        if(manager == null) {
            System.out.println("No such manager!");
            return true;
        }
        return false;
    }

    // project managing
    public void createProject(String name, String neighbourhood, HashMap<Flat.Type,Flat> flatInfo, LocalDate openDate,
                              LocalDate closeDate, String managerId, int officerSlots, boolean visibility) {
        HdbManager manager = managerRepo.getUser(managerId);
        if(check(manager)) return;
        Project project = new Project(name, neighbourhood, flatInfo, openDate, closeDate,
                    managerId, officerSlots, new ArrayList<String>(), visibility, new ArrayList<String>());
        if(manager.canManage(project)) { projectController.getRepo().addProject(project); }
        else { System.out.println("The manager can not manage the project at the time!"); }
    }

    public void editProject(String managerId, String name, int officerSlots) {
        HdbManager manager = managerRepo.getUser(managerId);
        if(check(manager)) return;
        Project project = projectController.getRepo().getProject(name);
        if(name.compareTo(manager.getManagedProject().getName()) != 0) {
            System.out.println("The manager is not managing the project!");
            return;
        }
        project.setOfficerSlots(officerSlots);
    }

    public void deleteProject(String managerId, String name) {
        HdbManager manager = managerRepo.getUser(managerId);
        if(check(manager)) return;
        if(name.compareTo(manager.getManagedProject().getName()) != 0) {
            System.out.println("The manager is not managing the project!");
            return;
        }
        projectController.getRepo().deleteProject(name);
    }

    public void toggleProjectVisibility(String managerId, String name) {
        HdbManager manager = managerRepo.getUser(managerId);
        if(check(manager)) return;
        if(name.compareTo(manager.getManagedProject().getName()) != 0) {
            System.out.println("The manager is not managing the project!");
            return;
        }
        projectController.getRepo().getProject(name).toggleVisibility();
    }

    public void viewCreatedProjects(String managerId) {
        HdbManager manager = managerRepo.getUser(managerId);
        if(check(manager)) return;
        projectController.displayProjectDashboard(manager);
    }

//    public void viewPendingOfficers() {
//        HdbOfficerController.getPendingOfficers().forEach(System.out::println);
//    }
//
//    public void viewApprovedOfficers() {
//        HdbOfficerController.getApprovedOfficers().forEach(System.out::println);
//    }

    public boolean processOfficerApplication(String managerId, String officerId, boolean status) {
        HdbManager manager = managerRepo.getUser(managerId);
        if(check(manager)) return false;

        ////////////////////////////////////////////////////////
        HdbOfficer officer = officerController.getRepo().getUser(officerId);
        if(officer == null) {
            System.out.println("No such officer!");
            return false;
        }
        if(!officer.hasAssignedProject()) {
            System.out.println("The officer has no application at the moment!");
            return false;
        }

        /////////////////////////////////////////////////////////
        TeamApplication application = officer.getTeamApplication();
        if(application == null) {
            System.out.println("No such application!");
            return false;
        }
        if(application.getProjectName().compareTo(manager.getManagedProject().getName()) != 0) {
            System.out.println("The manager is not managing the project!");
            return false;
        }
        if(application.getStatus() == Application.Status.SUCCESSFUL) {
            System.out.println("The officer is already approved!");
            return false;
        }
        if(application.getStatus() == Application.Status.UNSUCCESSFUL) {
            System.out.println("The officer is already rejected!");
            return false;
        }
        ///////////////////////////////////////////////////////////
        if(!status) {
            System.out.println("Successfully rejected!");
            application.updateStatus(Application.Status.UNSUCCESSFUL);
            return true;
        }

        ///////////////////////////////////////////////////////////
        Project project = projectController.getRepo().getProject(application.getProjectName());

        if(project.getOfficerSlots() == 0) {
            System.out.println("There is no slots left!");
            return false;
        }
        System.out.println("Successfully approved!");
        officer.assignProject(application.getProjectName());
        application.updateStatus(Application.Status.SUCCESSFUL);
        project.setOfficerSlots(project.getOfficerSlots() - 1);
        return true;
    }

    public boolean processApplicantBTOApplication(String managerId, String applicantId, boolean status) {
        HdbManager manager = managerRepo.getUser(managerId);
        if(check(manager)) return false;

        ////////////////////////////////////////////////////////
        ResidentialApplication application = resAppController.getRepo().getApplications().get(applicantId);
        if(application == null) {
            System.out.println("No such application!");
            return false;
        }

        /////////////////////////////////////////////////////////
        if(application.getProjectName().compareTo(manager.getManagedProject().getName()) != 0) {
            System.out.println("The manager is not managing the project!");
            return false;
        }
        if(application.getStatus() == Application.Status.SUCCESSFUL) {
            System.out.println("The officer is already approved!");
            return false;
        }
        if(application.getStatus() == Application.Status.UNSUCCESSFUL) {
            System.out.println("The officer is already rejected!");
            return false;
        }
        ///////////////////////////////////////////////////////////
        if(!status) {
            System.out.println("Successfully rejected!");
            application.updateStatus(Application.Status.UNSUCCESSFUL);
            return true;
        }

        ///////////////////////////////////////////////////////////

        System.out.println("Successfully approved!");
        application.updateStatus(Application.Status.SUCCESSFUL);
        return true;
    }

    public boolean approveApplicantWithdrawal(String managerId, String applicantId) {
        HdbManager manager = managerRepo.getUser(managerId);
        if(check(manager)) return false;
        ResidentialApplication application = resAppController.getRepo().getApplications().get(applicantId);
        if(application == null) {
            System.out.println("No such application!");
            return false;
        }
        if(application.getProjectName().compareTo(manager.getManagedProject().getName()) != 0) {
            System.out.println("The manager is not managing the project!");
            return false;
        }

        if(application.getStatus() == Application.Status.WITHDRAWN) {
            System.out.println("The application is already withdrawn!");
            return false;
        }
        if(application.getStatus() != Application.Status.WITHDRAWING) {
            System.out.println("The application must request for withdrawal!");
            return false;
        }
        application.updateStatus(Application.Status.WITHDRAWN);
        return true;
    }

    public boolean approveOfficerWithdrawal(String managerId, String officerId) {
        HdbManager manager = managerRepo.getUser(managerId);
        if(check(manager)) return false;
        TeamApplication application = teamAppController.getRepo().getApplications().get(officerId);
        if(application == null) {
            System.out.println("No such application!");
            return false;
        }
        if(application.getProjectName().compareTo(manager.getManagedProject().getName()) != 0) {
            System.out.println("The manager is not managing the project!");
            return false;
        }

        if(application.getStatus() == Application.Status.WITHDRAWN) {
            System.out.println("The application is already withdrawn!");
            return false;
        }
        if(application.getStatus() != Application.Status.WITHDRAWING) {
            System.out.println("The application must request for withdrawal!");
            return false;
        }
        application.updateStatus(Application.Status.WITHDRAWN);
        return true;
    }

    public void replyEnquiry(String managerId, String enquiryId, String response) {
        HdbManager manager = managerRepo.getUser(managerId);
        if(check(manager)) return;
        Enquiry enquiry = enquiryController.getEnquiry(enquiryId);
        if(enquiry == null) {
            System.out.println("No such enquiry!");
            return;
        }
        enquiry.respond(response);
    }
}