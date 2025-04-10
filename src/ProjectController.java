import java.util.*;
import java.util.stream.Collectors;

public class ProjectController{
    private static Scanner sc;

    //Dependencies
    private static ProjectUI projectUI;
    private static ProjectRepo projectRepo;

	public ProjectController(Scanner scanner) {
		sc = scanner;
        projectRepo = new ProjectRepo();
        projectUI = new ProjectUI(scanner,this);
	}

    public ProjectRepo getRepo(){
        return projectRepo;
    }

    public void exitMenu(){
        projectRepo.saveProjects();
    }

    public void displayProjectDashboard(Applicant applicant) {
        projectUI.displayProjectDashboard(applicant);
    }

    public HashMap<String,Project> filterByTypeAndDate(Flat.Type flatType){ //SOMETHING WRONG W FILTER
//        HashMap<String,Project> allProjects = projectRepo.getProjectListings();
//        return allProjects.entrySet().stream()
//                .filter(entry -> entry.getValue().hasAvailUnits(flatType) && entry.getValue().isWithinDateRange())
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (e1, e2) -> e1,
//                        HashMap::new
//                ));
        return null;
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


