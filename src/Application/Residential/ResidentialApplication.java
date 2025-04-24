package Application.Residential;
import Project.*;
import Application.*;
import Application.Team.*;

/**
 * Represents a residential application for HDB flats.
 * This class implements the Application interface and handles the application process
 * for residential properties, including status management and approval workflows.
 */
public class ResidentialApplication implements Application {

    private final String applicantId;   //can be applicantID or officerID depending on who
    private final String projectName;
    private final Flat.Type flatType;
    private Application.Status status;

    /**
     * Constructs a new ResidentialApplication with the specified details.
     *
     * @param applicantId The ID of the applicant (can be applicantID or officerID)
     * @param status The initial status of the application
     * @param projectName The name of the project being applied for
     * @param flatType The type of flat being applied for
     */
    public ResidentialApplication(String applicantId, ResidentialApplication.Status status, String projectName, Flat.Type flatType) {
        this.applicantId = applicantId;
        this.status = status;
        this.projectName = projectName;
        this.flatType = flatType;
    }

    /**
     * Gets the name of the project being applied for.
     *
     * @return The project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Gets the current status of the application.
     *
     * @return The application status
     */
    public TeamApplication.Status getStatus() {
        return status;
    }

    /**
     * Gets the type of flat being applied for.
     *
     * @return The flat type
     */
    public Flat.Type getFlatType() {
        return flatType;
    }

    /**
     * Gets the ID of the applicant.
     *
     * @return The applicant ID
     */
    public String getApplicantId() {
        return applicantId;
    }

    /**
     * Updates the status of the application.
     *
     * @param status The new status to set
     */
    public void updateStatus(Status status) {
        this.status = status;
    }
    
    /**
     * Sets the status of the application.
     *
     * @param status The new status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Approves the application if it is in a pending state.
     * If the application is in WITHDRAWING state, it will be marked as UNSUCCESSFUL.
     *
     * @return true if the approval was successful, false otherwise
     */
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

    /**
     * Rejects the application if it is in a pending state.
     *
     * @return true if the rejection was successful, false otherwise
     */
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
