public class TeamApplication implements Application{

    private final String officerID;
    private final String projectName;
    private Application.Status status;

    public TeamApplication(String officerID,String projectName, Status status){
        this.officerID = officerID;
        this.projectName = projectName;
        this.status = status;
    }

    public String getProjectName() {
        return projectName;
    }

    public Status getStatus() {
        return status;
    }

    public String getOfficerID(){
        return officerID;
    }

}
