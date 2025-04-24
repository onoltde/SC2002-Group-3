package Application;

/**
 * Represents a housing application within the BTO system.
 * Provides methods to retrieve the project name and application status.
 */
public interface Application {

    /**
     * Enum representing the various statuses an application can have.
     */
    public enum Status {
        /** The application has been submitted and is awaiting processing. */
        PENDING,
        /** The application was successful. */
        SUCCESSFUL,
        /** The application was unsuccessful. */
        UNSUCCESSFUL,
        /** A flat has been booked for the application. */
        BOOKED,
        /** The application is currently in the booking process. */
        BOOKING,
        /** The application is in the process of being withdrawn. */
        WITHDRAWING,
        /** The application has been withdrawn. */
        WITHDRAWN;
    }

    /**
     * Retrieves the name of the BTO project associated with the application.
     *
     * @return the project name
     */
    String getProjectName();

    /**
     * Retrieves the current status of the application.
     *
     * @return the application status
     */
    Status getStatus();

}
