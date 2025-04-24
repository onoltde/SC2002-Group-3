package Application;

/**
 * Interface representing an application in the HDB system.
 * This interface defines the common structure and behavior for all types of applications,
 * including residential and team applications.
 */
public interface Application {

    /**
     * Enum representing the possible statuses of an application.
     * PENDING - Application is under review
     * SUCCESSFUL - Application has been approved
     * UNSUCCESSFUL - Application has been rejected
     * BOOKED - Unit has been booked
     * BOOKING - In the process of booking a unit
     * WITHDRAWING - Application is in the process of being withdrawn
     * WITHDRAWN - Application has been withdrawn
     */
    public enum Status {
        PENDING,
        SUCCESSFUL,
        UNSUCCESSFUL,
        BOOKED,
        BOOKING,
        WITHDRAWING,
        WITHDRAWN;
    }

    /**
     * Gets the name of the project being applied for.
     *
     * @return The project name
     */
    String getProjectName();

    /**
     * Gets the current status of the application.
     *
     * @return The application status
     */
    Status getStatus();
}
