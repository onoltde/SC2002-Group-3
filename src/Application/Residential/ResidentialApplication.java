package Application.Residential;
import Project.*;
import Application.*;
import Application.Team.*;


public class ResidentialApplication implements Application {

    private final String applicantId;   //can be applicantID or officerID depending on who
    private final String projectName;
    private final Flat.Type flatType;
    private Application.Status status;

    public ResidentialApplication(String applicantId, ResidentialApplication.Status status, String projectName, Flat.Type flatType) {
        this.applicantId = applicantId;
        this.status = status;
        this.projectName = projectName;
        this.flatType = flatType;
    }

    public String getProjectName() {
        return projectName;
    }

    public TeamApplication.Status getStatus() {
        return status;
    }

    public Flat.Type getFlatType() {
        return flatType;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean approve(){
        if(this.status == Status.PENDING){
            this.status = Status.SUCCESSFUL;
            System.out.println("Application is now Successful!");
            return true;
        }else if(this.status == Status.WITHDRAWING){
            this.status = Status.UNSUCCESSFUL;
            System.out.println("Application is now Withdrawn!");
            return true;
        }
        else{
            return false;
        }
    }

    public boolean reject(){
        if(this.status == Status.PENDING){
            this.status = Status.UNSUCCESSFUL;
            System.out.println("Application is now Unsucessful!");
            return true;
        }else{
            return false;
        }
    }
}
