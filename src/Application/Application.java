package Application;

public interface Application {

    public enum Status {
        PENDING,
        SUCCESSFUL,
        UNSUCCESSFUL,
        BOOKED,
        WITHDRAWING;
    }

    String getProjectName();

    Status getStatus();

}
