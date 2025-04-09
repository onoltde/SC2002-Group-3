public interface Application {

    public enum Status {
        PENDING,
        SUCCESSFUL,
        UNSUCCESSFUL,
        BOOKED;
    }

    public String getProjectName();

    public TeamApplication.Status getStatus();

}
