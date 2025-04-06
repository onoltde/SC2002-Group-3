public class Application {
    private Project project;
    private String flatType;
    private ApplicationStatus status;

    public Application(Project project, String flatType, ApplicationStatus status) {
        this.project = project;
        this.flatType = flatType;
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getFlatType() {
        return flatType;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
