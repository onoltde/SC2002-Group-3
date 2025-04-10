import java.util.HashMap;

public class ProjectUI {

    public void displayProjects(HashMap<String,Project> projectListing){
        projectListing.values().stream()
                .forEach(project -> System.out.println(project.toString()));
    }
}
