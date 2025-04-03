import java.util.*;
import java.time.*;

public class Project {
	private final String name;
	private final String neighbourhood;
	private ArrayList<Flat> flatInfo;
	private final LocalDate openDate;
	private final LocalDate closeDate;
	private String managerId;
	private int officerSlots;
	private ArrayList<String> assignedOfficers;
	private boolean visibility;
	private ArrayList<String> pendingOfficers;

	public Project(String name, String neighbourhood, ArrayList<Flat> flatInfo, LocalDate openDate, LocalDate closeDate, String managerId, int officerSlots, ArrayList<String> assignedOfficers, boolean visibility, ArrayList<String> pendingOfficers ) {
		this.name = name;
		this.neighbourhood = neighbourhood;
		this.flatInfo = flatInfo;
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.managerId = managerId;
		this.officerSlots = officerSlots;
		this.assignedOfficers = assignedOfficers;
		this.visibility = visibility;
		this.pendingOfficers = pendingOfficers;
	}

	public void addPendingOfficer(String officerId) {pendingOfficers.add(officerId);}
	public void removePendingOfficer(String officerId){pendingOfficers.remove(officerId);}
	public void addAssignedOfficer(String officerId) {
		assignedOfficers.add(officerId);
		//include check for slots available logic
	}
	public void removeAssignedOfficer(String officerId){assignedOfficers.remove(officerId);}
	//getters
	public String getName() { return name; }
	public String getNeighbourhood() { return neighbourhood; }
	public LocalDate getOpenDate() { return openDate; }
	public LocalDate getCloseDate() { return closeDate; }
	public ArrayList<Flat> getFlatInfo(){ return flatInfo; }
	public String getManagerId() { return managerId; }
	public int getOfficerSlots() { return officerSlots; }
	public void setOfficerSlots(int officerSlots) { this.officerSlots = officerSlots; }
	public ArrayList<String> getAssignedOfficers() { return new ArrayList<>(assignedOfficers); }
	public boolean isVisible() { return visibility; }
	public ArrayList<String> getPendingOfficers() { return new ArrayList<>(pendingOfficers); }



}