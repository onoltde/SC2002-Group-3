import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ProjectController {
	private ArrayList<Project> projectListings;
	
	public ProjectController() {
		projectListings = new ArrayList<Project>();
	}
	
	public void createProjectListing(String id, String n, String nh, HashMap ft, Date o, Date c, String manID, int slots) {
		Project newProject = new Project(id, n, nh, ft, o, c, manID, slots);
		projectListings.add(newProject);
	}
	
	public Project getProjectListing(String id) {
		for (int i = 0; i < projectListings.size(); i++) {
			if (projectListings.get(i).getID() == id)
				return projectListings.get(i);
		}
		return null;
	}
	
	public void deleteProjectListing(String id) {
		for (int i = 0; i < projectListings.size(); i++) {
			if (projectListings.get(i).getID() == id)
				projectListings.remove(i);
		}
	}
	
	public void toggleProjectVisibility(String id, boolean vis) {
		for (int i = 0; i < projectListings.size(); i++) {
			if (projectListings.get(i).getID() == id)
				projectListings.get(i).setVisivility(vis);
		}
	}
	
	public void viewAllProjectListings(HDBManager man, boolean own) {
		// only see projects created by the manager
		String id = man.getManagerId();
		if (own) {
			for (int i = 0; i < projectListings.size(); i++) {
				if (projectListings.get(i).getManager() == id) {
					System.out.println(projectListings.get(i).getID());
					System.out.println(projectListings.get(i).getName());
					System.out.println(projectListings.get(i).getNeighbourhood());
					System.out.println(projectListings.get(i).getApplicationOpenDate());
					System.out.println(projectListings.get(i).getApplicationCloseDate());
					if (projectListings.get(i).getVisibility())
						System.out.println("On");
					else
						System.out.println("Off");
				}
			}
		}
		else {
			for (int i = 0; i < projectListings.size(); i++) {
				System.out.println(projectListings.get(i).getID());
				System.out.println(projectListings.get(i).getName());
				System.out.println(projectListings.get(i).getNeighbourhood());
				System.out.println(projectListings.get(i).getApplicationOpenDate());
				System.out.println(projectListings.get(i).getApplicationCloseDate());
				if (projectListings.get(i).getVisibility())
					System.out.println("On");
				else
					System.out.println("Off");
			}
		}
	}
	
	public void viewAllProjectListings(Applicant man, boolean own) {
		// only see projects created by the manager
		String id = man.getManagerId();
		if (own) {
			for (int i = 0; i < projectListings.size(); i++) {
				if (projectListings.get(i).getManager() == id) {
					System.out.println(projectListings.get(i).getID());
					System.out.println(projectListings.get(i).getName());
					System.out.println(projectListings.get(i).getNeighbourhood());
					System.out.println(projectListings.get(i).getApplicationOpenDate());
					System.out.println(projectListings.get(i).getApplicationCloseDate());
					if (projectListings.get(i).getVisibility())
						System.out.println("On");
					else
						System.out.println("Off");
				}
			}
		}
		else {
			for (int i = 0; i < projectListings.size(); i++) {
				System.out.println(projectListings.get(i).getID());
				System.out.println(projectListings.get(i).getName());
				System.out.println(projectListings.get(i).getNeighbourhood());
				System.out.println(projectListings.get(i).getApplicationOpenDate());
				System.out.println(projectListings.get(i).getApplicationCloseDate());
				if (projectListings.get(i).getVisibility())
					System.out.println("On");
				else
					System.out.println("Off");
			}
		}
	}
}
