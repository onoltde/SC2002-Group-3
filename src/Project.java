import java.util.*;


public class Project {
    private String projectID;
    private String name;
    private String neighbourhood;
    private HashMap<String,Integer> flatTypes;
    private Date applicationOpenDate;
    private Date applicationCloseDate;
    private boolean visibility;
    private HdbManager manager;
    private ArrayList<HdbOfficer> assignedOfficers;
    private int officerSlots;

    public Project(String id, String n, String nh, HashMap ft, Date o, Date c, HdbManager man, int slots) {
        projectID = id;
        name = n;
        neighbourhood = nh;
        flatTypes = new HashMap<String, Integer>(ft);
        applicationOpenDate = o;
        applicationCloseDate = c;
        visibility = false;
        manager = man;
        assignedOfficers = new ArrayList<HdbOfficer>();
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

    public HdbManager getManager() {return manager;}

    public ArrayList<HdbOfficer> getOfficers() {return assignedOfficers;}
    public void addOfficer(HdbOfficer officer) {
        int size = assignedOfficers.size();
        if (size < officerSlots)
            assignedOfficers.add(officer);
    }
}