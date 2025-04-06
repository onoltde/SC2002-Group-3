
import java.time.LocalDateTime;

public class BookedFlat {
    private String flatType;
    private String projectName;
    private LocalDateTime bookedOn;

    public BookedFlat(String flatType, String projectName, LocalDateTime bookedOn) {
        this.flatType = flatType;
        this.projectName = projectName;
        this.bookedOn = bookedOn;
    }

    public String getFlatType() {
        return flatType;
    }

    public String getProjectName() {
        return projectName;
    }

    public LocalDateTime getBookedOn() {
        return bookedOn;
    }
}
