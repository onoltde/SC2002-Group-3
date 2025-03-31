import java.util.*;

public class Project {
	private String projectID;
	private String name;
	private String neighbourhood;
	private HashMap<String,Integer> flatTypes;
	private Date applicationOpenDate;
	private Date applicationCloseDate;
	private boolean visibility;
	private String manager;
	private ArrayList<String> assignedOfficers;
	private ArrayList<String> pendingOfficers;
	private int officerSlots;
	
	public Project(String id, String n, String nh, HashMap ft, Date o, Date c, String manID, int slots) {
		projectID = id;
		name = n;
		neighbourhood = nh;
		flatTypes = new HashMap<String, Integer>(ft);
		applicationOpenDate = o;
		applicationCloseDate = c;
		visibility = false;
		manager = manID;
		assignedOfficers = new ArrayList<String>();
		officerSlots = slots;
	}
	
	public String getId() {return projectID;}
	public void setID(String id) {projectID = id;}
	
	public String getName() {return name;}
	public void setName(String n) {name = n;}
	
	public String getNeighbourhood() {return neighbourhood;}
	public void setNeighbourhood(String n) {neighbourhood = n;}
	
	public int getNumOfUnits(String type) {return flatTypes.get(type);}
	public void setNumOfUnits(String type, int num) {flatTypes.put(type, num);}
	
	public Date getApplicationOpenDate() {return applicationOpenDate;}
	public void setApplicationOpenDate(Date d) {applicationOpenDate = d;} 
	
	public Date getApplicationCloseDate() {return applicationCloseDate;}
	public void setApplicationCloseDate(Date d) {applicationCloseDate = d;} 
	
	public boolean getVisibility() {return visibility;}
	public void setVisivility(boolean b) {visibility = b;}
	
	public String getManager() {return manager;}
	
	public ArrayList<String> getOfficers() {return assignedOfficers;}
	public void addOfficer(String id) {
		int size = assignedOfficers.size();
		if (size < officerSlots)
			assignedOfficers.add(id);
	}
	
	public ArrayList<String> getPendingOfficers(){return pendingOfficers;}
	public void addPendingOfficer(String id) {pendingOfficers.add(id)}
	public void removePendingOfficer(String id) {
		for (int i = 0; i < pendingOfficers.size(); i++) {
			if (pendingOfficers.get(i) == id) {
				pendingOfficers.remove(i);
				break;
			}
		}
	}
	
	public int getOfficerSlots() {return officerSlots;}
	public void setOfficerSlots(int num) {officerSlots = num;}
}