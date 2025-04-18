package Project;
import Applicant.*;
import Application.Residential.ResidentialApplicationController;
import Application.Residential.ResidentialApplicationRepo;
import Application.Team.TeamApplicationRepo;
import Enquiry.EnquiryController;
import HdbManager.*;
import HdbOfficer.*;
import Users.*;
import Application.*;

import java.util.HashMap;

public class ProjectController implements ProjectControllerInterface {

    private static ProjectUI projectUI;
    private static ProjectRepo projectRepo;
    private HashMap<String, HdbManager> managerMap;
    private HashMap<String, HdbOfficer> officerMap;

    public ProjectController() {
        projectRepo = new ProjectRepo();
        projectUI = new ProjectUI(this);
        managerMap = new HdbManagerRepo(this).getManagers();
        officerMap = new HdbOfficerRepo(new ResidentialApplicationRepo(), new TeamApplicationRepo()).getOfficers();
    }

    public void saveChanges(){
        projectRepo.saveProjects();
    }

    public ProjectRepo getRepo(){
        return projectRepo;
    }

    public Project getProject(String projectName) {
        return projectRepo.getProject(projectName);
    }

    //applicant methods
    public void displayProjectDashboard(Applicant applicant, ResidentialApplicationController residentialApplicationController) {
        projectUI.displayProjectDashboard(applicant, residentialApplicationController);
    }

    public void applyProject(Applicant applicant, Project currentProject, Flat.Type flatType){
        ResidentialApplicationController resAppController = new ResidentialApplicationController(this);
        resAppController.applyProject(applicant,currentProject,flatType);
    }

    //offcier methods
    public String displayTeamProjectsToApply(HdbOfficer officer){
        return projectUI.displayTeamProjectsToApply(officer);
    }

    public void displayResProjectsToApply(HdbOfficer officer, Flat.Type flatType){
        if (flatType == Flat.Type.TWOROOM){
            projectUI.displayTwoRoomResProjectsToApply(officer);
        }else if (flatType == Flat.Type.THREEROOM){
            projectUI.displayThreeRoomResProjectsToApply(officer);
        }
    }

    public boolean bookFlat(String projectName, Flat.Type flatType){        //used to approve someones booking
        Project project = projectRepo.getProject(projectName);
        Flat flat= project.getFlatInfo().get(flatType);
        return flat.bookUnit();
    }

    //manager methods
    public void displayProjectDashboard(HdbManager manager) {
        projectUI.displayProjectDashboard(manager);
    }


    //projectUI methods
    public void displayProjectFlatDetails(String projectName, Flat.Type flatType){
        Project project = projectRepo.getProject(projectName);
        projectUI.displayEssentialProjectDetails(project);
        if (flatType == Flat.Type.TWOROOM){
            projectUI.displayFlatDetails(project.getFlatInfo().get(Flat.Type.TWOROOM));
        }else if (flatType == Flat.Type.THREEROOM){
            projectUI.displayFlatDetails((project.getFlatInfo().get(Flat.Type.THREEROOM)));
        }

    }

    public void displayAdminProjectDetails(String projectName){
        Project project = projectRepo.getProject(projectName);
        projectUI.displayEssentialProjectDetails(project);
        projectUI.displayFlatDetails(project.getFlatInfo().get(Flat.Type.TWOROOM));
        projectUI.displayFlatDetails(project.getFlatInfo().get(Flat.Type.THREEROOM));
        projectUI.displayProjectAdminDetails(project, managerMap, officerMap);
    }
    
}

//	// Need to check if he is managing any other "active" projects
//	public void createProjectListing(String id, String n, String nh, HashMap ft, Date o, Date c, String manID, int slots) {
//		Project newProject = new Project(id, n, nh, ft, o, c, manID, slots);
//		projectListings.add(newProject);
//	}
//
//	public Project getProjectListing(String id) {
//		for (int i = 0; i < projectListings.size(); i++) {
//			if (projectListings.get(i).getId() == id)
//				return projectListings.get(i);
//		}
//		return null;
//	}
//
//	public void deleteProjectListing(String id) {
//		for (int i = 0; i < projectListings.size(); i++) {
//			if (projectListings.get(i).getId() == id)
//				projectListings.remove(i);
//		}
//	}
//
//	public void toggleProjectVisibility(String id, boolean vis) {
//		for (int i = 0; i < projectListings.size(); i++) {
//			if (projectListings.get(i).getId() == id)
//				projectListings.get(i).setVisivility(vis);
//		}
//	}
//
//	public void viewAllProjectListings(HDBManager man, boolean own) {
//		String id = man.getId();
//		// only see projects created by the manager
//		if (own) {
//			for (int i = 0; i < projectListings.size(); i++) {
//				if (projectListings.get(i).getManager() == id) {
//					System.out.println(projectListings.get(i).getID());
//					System.out.println(projectListings.get(i).getName());
//					System.out.println(projectListings.get(i).getNeighbourhood());
//					System.out.println(projectListings.get(i).getApplicationOpenDate());
//					System.out.println(projectListings.get(i).getApplicationCloseDate());
//					if (projectListings.get(i).getVisibility())
//						System.out.println("On");
//					else
//						System.out.println("Off");
//				}
//			}
//		}
//		else {
//			for (int i = 0; i < projectListings.size(); i++) {
//				System.out.println(projectListings.get(i).getID());
//				System.out.println(projectListings.get(i).getName());
//				System.out.println(projectListings.get(i).getNeighbourhood());
//				System.out.println(projectListings.get(i).getApplicationOpenDate());
//				System.out.println(projectListings.get(i).getApplicationCloseDate());
//				if (projectListings.get(i).getVisibility())
//					System.out.println("On");
//				else
//					System.out.println("Off");
//			}
//		}
//	}
//
//	// only see projects with visibility turned on and based on their marital status
//	public void viewAllProjectListings(Applicant app) {
//		String id = app.getId();
//		boolean married = app.getMaritalStatus();
//		for (int i = 0; i < projectListings.size(); i++) {
//			if (projectListings.get(i).getVisibility()) {
//				System.out.println(projectListings.get(i).getID());
//				System.out.println(projectListings.get(i).getName());
//				System.out.println(projectListings.get(i).getNeighbourhood());
//				System.out.println(projectListings.get(i).getApplicationOpenDate());
//				System.out.println(projectListings.get(i).getApplicationCloseDate());
//				System.out.println("On");
//			}
//		}
//	}
//
//	// Applicant/HdbOfficer can view assigned project regardless of visibility
//	public void viewOwnProjectListing(Applicant app) {
//		String id = app.getProject();
//		for (int i = 0; i < projectListings.size(); i++) {
//			if (projectListings.get(i).getId() == id) {
//				System.out.println(projectListings.get(i).getID());
//				System.out.println(projectListings.get(i).getName());
//				System.out.println(projectListings.get(i).getNeighbourhood());
//				System.out.println(projectListings.get(i).getApplicationOpenDate());
//				System.out.println(projectListings.get(i).getApplicationCloseDate());
//				if (projectListings.get(i).getVisibility())
//					System.out.println("On");
//				else
//					System.out.println("Off");
//				break;
//			}
//		}
//	}
//
//	// Parse in HdbOfficer to ensure only HdbOfficer can apply
//	public void hdbOfficerRegistration(HdbOfficer officer, String projectId) {
//		String id = officer.getId();
//		for (int i = 0; i < projectListings.size(); i++) {
//			if (projectListings.get(i).getId() == projectId) {
//				projectListings.get(i).addPendingOfficer(id);
//				break;
//			}
//
//		}
//	}
//
//	public void viewPendingHdbOfficerRegistration(String manId, String id) {
//		ArrayList<String> currPending = new ArrayList<String>();
//		for (int i = 0; i < projectListings.size(); i++) {
//			if (projectListings.get(i).getId() == projectId) {
//				if (projectListings.get(i).getManager() == mandId)
//					currPending = projectListings.get(i).getPendingOfficers();
//				else
//					System.out.println("You do not have the permission to view the HDB Officers' applications");
//				break;
//			}
//
//		}
//
//		if (currPending.size() > 0) {
//			System.out.println("Current pending HDB Officers:");
//			for (int i = 0; i< currPending.size(); i++) {
//				System.out.println(currPending.get(i));
//			}
//		}
//	}
//
//	public void approveHdbOfficerRegistration(String manID, String id, boolean approve) {
//		ArrayList<String> currPending = new ArrayList<String>();
//		for (int i = 0; i < projectListings.size(); i++) {
//			if (projectListings.get(i).getId() == projectId) {
//				if (projectListings.get(i).getManager() == mandId) {
//					projectListings.get(i).removePendingOfficer(id);
//					if (approve) {
//						projectListings.get(i).addOfficer(id);
//						int update = projectListings.get(i).getOfficerSlots() - 1;
//						projectListings.get(i).setOfficerSlots(update);
//					}
//
//				}
//				else
//					System.out.println("You do not have the permission to view the HDB Officers' applications");
//				break;
//			}
//
//		}
//	}


