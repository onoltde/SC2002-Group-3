public interface Application {

    public enum Status {
        PENDING,
        SUCCESSFUL,
        UNSUCCESSFUL,
        BOOKED;
    }

    String getProjectName();

    Status getStatus();

}
