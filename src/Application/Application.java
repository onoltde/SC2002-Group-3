package Application;

public interface Application {

    public enum Status {
        PENDING,
        SUCCESSFUL,
        UNSUCCESSFUL,
        BOOKED,
        BOOKING,
        WITHDRAWING,
        WITHDRAWN;
    }

    String getProjectName();

    Status getStatus();

}
