/**
 * Represents an HDB Manager who can manage BTO projects and related operations.
 * Extends the User class to inherit basic user properties.
 */
public class HdbManager extends User {

    private final String managerId;
    private Project managedProject;

    /**
     * Constructor for HdbManager.
     * @param name Manager's name
     * @param nric Manager's NRIC
     * @param age Manager's age
     * @param maritalStatus Manager's marital status
     * @param password Manager's password
     */
    public HdbManager(String name,String nric, int age, MaritalStatus maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.managerId = "MA-" + nric.substring(5);
    }

    public String getId(){return this.managerId;}

    @Override
    public String toString() {
        return "Manager" + super.toString() + ", ID: " + managerId + "}" ;
    }


//    // Project Management
//    /**
//     * Creates a new BTO project listing
//     * @param projectDetails The details of the new project
//     */
//    public void createProjectListing(Project projectDetails) {
//        if (managedProject == null || !isDuringApplicationPeriod(projectDetails)) {
//            projectDetails.manager = this.nric;
//            ProjectController.addProject(projectDetails);
//        } else {
//            throw new IllegalStateException("Already managing a project during this period");
//        }
//    }
//
//    /**
//     * Edits an existing project listing
//     * @param projectID ID of project to edit
//     * @param updatedDetails New project details
//     */
//    public void editProjectListing(String projectID, Project updatedDetails) {
//        this.managedProject = ProjectController.editProjectListing(projectID, updatedDetails);
//    }
//
//    /**
//     * Deletes a project listing
//     * @param projectID ID of project to delete
//     */
//    public void deleteProjectListing(String projectID) {
//        ProjectController.deleteProjectListing(projectID);
//    }
//
//    // Visibility Control
//    /**
//     * Toggles project visibility
//     * @param projectID ID of project to modify
//     */
//    public void toggleProjectVisibility(String projectID) {
//        Project project = ProjectController.getProject(projectID);
//        if (project != null) {
//            project.setVisible(!project.isVisible());
//        }
//    }
//
//    // Project Views
//    /**
//     * Views all projects in the system
//     * @param projects List of all projects
//     */
//    public void viewAllProjects(List<Project> projects) {
//        projects.forEach(System.out::println);
//    }
//
//    /**
//     * Views projects created by this manager
//     * @param projects List of all projects
//     */
//    public void viewCreatedProjects(List<Project> projects) {
//        projects.stream()
//                .filter(p -> p.getManager().equals(this))
//                .forEach(System.out::println);
//    }
//
//    // Officer Management
//    /**
//     * Approves an officer registration application
//     * @param officerId NRIC of officer to approve
//     * @return true if approval successful
//     */
//    public boolean approveOfficerApplication(String officerId) {
//        HDBOfficer officer =HDBOfficerController.getOfficer(officerId);
//        if(officer.getAssignedProject == null) return false;
//        ResidentialApplication application = ApplicationController.getResidentialApplicationApplication(officerId);
//        application.updateStatus(true);
//        officer.setAssignedProject(application.getID);
//    }
//
//    /**
//     * Views pending officer registrations
//     */
//    public void viewPendingOfficer() {
//        ApplicationController.getPendingApplications().forEach(System.out::println);
//    }
//
//    /**
//     * Views approved officers
//     */
//    public void viewApprovedOfficer() {
//        HDBOfficerController.getApprovedOfficers()
//                .forEach(System.out::println);
//    }
//
//    // Applicant Management
//    /**
//     * Approves an applicant's BTO application
//     * @param applicationId Applicant's application ID
//     * @return true if approval successful
//     */
//    public boolean approveApplicantBTOApplication(String applicationId) {
//        ResidentialApplication application = ApplicationController
//                .getResidentialApplicationApplication(applicationId);
//
//        if (application != null && managedProject.hasAvailableUnits(application.getFlatType())) {
//            application.approve();
//            managedProject.reduceAvailableUnits(application.getFlatType());
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * Approves an applicant's withdrawal request
//     * @param applicationId Applicant's application ID
//     * @return true if approval successful
//     */
//    public boolean approveApplicantWithdrawal(String applicationId) {
//        Applciation application = ApplicationController.getResidentialApplicationApplication(applicationId);
//        if (application != null) {
//            managedProject.increaseAvailableUnits(application.getFlatType());
//            application.withdraw();
//            return true;
//        }
//        return false;
//    }
//
//    // Enquiry Management
//    /**
//     * Views all enquiries across projects
//     */
//    public void viewEnquiry() {
//        EnquiryController.getAllEnquiries()
//                .forEach(System.out::println);
//    }
//
//    /**
//     * Replies to an enquiry
//     * @param enquiryId ID of enquiry to reply to
//     * @param response Response message
//     */
//    public void replyEnquiry(String enquiryId, String response) {
//        Enquiry enquiry = EnquiryController.getEnquiry(enquiryId);
//        if (enquiry != null) {
//            enquiry.addResponse(response);
//        }
//    }
//
//    private boolean isDuringApplicationPeriod(Project project) {
//        if(this.managedProject == null) return true;
//        if(managedProject.getApplicatioCloseDate() < project.getApplicationStartDate()) return true;
//        return false;
//    }
}


//    public void createProjectListing(String projectId, String name, String neighbourhood, HashMap<String,Integer> flatTypes,
//                                        Date applicationOpenDate, Date applicationCloseDate, int officerSlots) {
//        if (this.isDuringApplicationPeriod(applicationOpenDate) ||
//            this.isDuringApplicationPeriod(applicationCloseDate)) {
//            System.out.println("Period is overlapping!!");
//            return;
//        }
//        ProjectController.createProjectListing( projectId, name, neighbourhood, flatTypes,
//                                                applicationOpenDate, applicationCloseDate, this.managerId, officerSlots);
//        managedProjects.add(projectId);
//    }
//
//    public void editProjectListing(String projectId, String name, String neighbourhood,
//                                    Date applicationOpenDate,Date applicationCloseDate) {
//        Project project = ProjectController.getProjectListing(projectId);
//        if(project == null) {
//            System.out.println("Such project doesn't exist!!");
//            return;
//        }
//        if(!this.isManaging(projectId)) {
//            System.out.println("Managers can only edit projects managed by themselves!!!!");
//            return;
//        }
//        project.setName(name);
//        project.setNeighbourhood(neighbourhood);
//        project.setApplicationOpenDate(applicationOpenDate);
//        project.setApplicationCloseDate(applicationCloseDate);
//    }
//
//    public void deleteProjectListing(String projectId) {
//        Project project = ProjectController.getProjectListing(projectId);
//        if(project == null) {
//            System.out.println("Such project doesn't exist!!");
//            return;
//        }
//        if(!this.isManaging(projectId)) {
//            System.out.println("Managers can only delete projects managed by themselves!!!!");
//            return;
//        }
//        ProjectController.deleteProjectListing(projectId);
//        managedProjects.remove(projectId);
//    }
//
//    public void toggleProjectVisibility(String projectId) {
//        Project project = ProjectController.getProjectListing(projectId);
//        if(project == null) {
//            System.out.println("Such project doesn't exist!!");
//            return;
//        }
//        if(!this.isManaging(projectId)) {
//            System.out.println("Managers can only toggle visibility of projects managed by themselves!!");
//            return;
//        }
//        ProjectController.toggleProjectVisibility(projectId); // !!!!!
//    }
//
//    public boolean approveOfficerApplication(String officerId) {
//        HdbOfficer officer = HdbOfficerController.getOfficer(officerId);
//
//        if(officer == null) {
//            System.out.println("No such officer");
//            return false;
//        }
//
//        if(officer.getAssignedProject() == null) {
//            System.out.println("The officer didn't apply for any project!!");
//            return false;
//        }
//
//        ResidentialApplication application = ApplicationController.getApplicationByApplicant(officerId);
//        if(!this.isManaging(application.getProjectId())) {
//            System.out.println("The manager is not managing this project!!");
//            return false;
//        }
//        application.approve();
//        return true;
//    }
//
//    public boolean approveApplicantBTOApplication(String applicationId) {
//        ResidentialApplication application = ApplicationController.getResidentialApplicationApplication(applicationId);
//        if(application == null) {
//            System.out.println("No such application!!");
//            return false;
//        }
//        if(!this.isManaging(application.getProjectId())) {
//            System.out.println("The manager is not managing this project!!");
//            return false;
//        }
//        application.approve();
//        return true;
//    }
//
//    public boolean approveApplicantWithdrawal(String applicationId) {
//        ResidentialApplication application = ApplicationController.getResidentialApplicationApplication(applicationId);
//        if (application == null) {
//            System.out.println("No such application!!");
//            return false;
//        }
//        if(!this.isManaging(application.getProjectId())) {
//            System.out.println("Can approve withdrawal on only managed projects");
//            return false;
//        }
//        application.withdraw();
//        return true;
//    }
//
//    // public void viewEnquiry() {
//    //     ProjectEnquiryController.getAllEnquiries()
//    //             .forEach(System.out::println);
//    // }
//
//    public void replyEnquiry(String enquiryId, String response) {
//        Enquiry enquiry = EnquiryController.getEnquiry(enquiryId);
//        if (enquiry == null) {
//            System.out.println("No such enquiry!!");
//            return;
//        }
//        String projectId = enquiry.getProjectId();
//        if(!this.isManaging(projectId)) {
//            System.out.println("The manager is not managing this project!!");
//            return;
//        }
//        EnquiryController.replyEnquiry(enquiryId, response);
//        return;
//    }
//
//    public boolean isDuringApplicationPeriod(Date date) {
//        for (String projectId : managedProjects) {
//            Project project = ProjectController.getProjectListing(projectId);
//            if (project == null) continue;
//            if (!date.before(project.getApplicationOpenDate()) && !date.after(project.getApplicationCloseDate())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean isManaging(String projectId) {
//        return managedProjects.contains(projectId);
//    }
//}
