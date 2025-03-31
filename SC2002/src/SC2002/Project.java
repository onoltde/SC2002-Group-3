package SC2002;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

public class Project {
	private String projectID;
	private String name;
	private String neighbourhood;
	private HashMap<String,Integer> flatTypes;
	private Date applicationOpenDate;
	private Date applicationCloseDate;
	private boolean visibility;
	private HDBManager manager;
	private ArrayList<HDBOfficer> assignedOfficers;
	private int officerSlots;
	
	public Project(String id, String n, String nh, HashMap ft, Date o, Date c, HDBManager man, int slots) {
		projectID = id;
		name = n;
		neighbourhood = nh;
		flatTypes = new HashMap<String, Integer>(ft);
		applicationOpenDate = o;
		applicationCloseDate = c;
		visibility = false;
		manager = man;
		assignedOfficers = new ArrayList<HDBOfficer>();
		officerSlots = slots;
	}
	
	public String getID() {return projectID;}
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
	
	public HDBManager getManager() {return manager;}
	
	public ArrayList<HDBOfficer> getOfficers() {return assignedOfficers;}
	public void addOfficer(HDBOfficer officer) {
		int size = assignedOfficers.size();
		if (size < officerSlots)
			assignedOfficers.add(officer);
	}
}

