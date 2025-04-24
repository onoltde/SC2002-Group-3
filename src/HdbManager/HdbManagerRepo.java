package HdbManager;
import Project.ProjectController;
import Project.ProjectControllerInterface;
import Users.*;
import java.util.*;
import java.io.*;

/**
 * A repository for managing HDB managers, including loading from and saving to a CSV file.
 */
public class HdbManagerRepo implements UserRepo<HdbManager> {
    private static final String FILE_PATH = "data\\ManagerList.csv";  // Constant for file path
    private static final String NULL_PROJECT = "null";  // Constant for "null" project
    private static final String HEADER = "Name,NRIC,Age,Marital Status,Password,ManagerId,AssignedProjectName";  // Column header
    private ProjectControllerInterface projectController;
    private HashMap<String, HdbManager> managers;

    /**
     * Constructor for HdbManagerRepo.
     * @param projectController Project controller used to retrieve project details.
     */
    public HdbManagerRepo(ProjectControllerInterface projectController) {
        this.managers = new HashMap<>();
        this.projectController = projectController;
        loadFile();
    }

    /**
     * Loads manager data from the CSV file and populates the repository.
     */
    public void loadFile() {
        File file = new File(FILE_PATH);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();  // Skip the header line

            String line = br.readLine();
            while (line != null) {
                String[] values = line.split(",");
                if (values.length >= 5) {
                    String name = values[0].trim();
                    String nric = values[1].trim();
                    int age = Integer.parseInt(values[2].trim());
                    String password = values[4].trim();
                    String projectName = values[6].trim();
                    User.MaritalStatus maritalStatus = User.MaritalStatus.valueOf(values[3].trim().toUpperCase());

                    // Create new manager object
                    HdbManager newManager = new HdbManager(name, nric, age, maritalStatus, password,
                            NULL_PROJECT.equals(projectName) ? null : projectController.getRepo().getProject(projectName));
                    addUser(newManager);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Saves manager data to the CSV file.
     */
    public void saveFile() {
        File file = new File(FILE_PATH);
        try {
            // Clear the file before rewriting
            new FileOutputStream(file).close();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(HEADER);
                bw.newLine();

                for (HdbManager managerPerson : managers.values()) {
                    String line = String.join(",",
                            managerPerson.getName(),
                            managerPerson.getNric(),
                            String.valueOf(managerPerson.getAge()),
                            managerPerson.getMaritalStatus().toString(),
                            managerPerson.getPassword(),
                            managerPerson.getId(),
                            (managerPerson.getManagedProject() == null ? NULL_PROJECT :
                                    managerPerson.getManagedProject().getName())
                    );
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Generates a unique ID for the manager based on their NRIC.
     * @param nric The NRIC of the manager.
     * @return A generated manager ID.
     */
    public String generateID(String nric) {
        return "MA-" + nric.substring(5);  // Extracts the last part of the NRIC for the manager ID
    }

    /**
     * Adds a new manager to the repository.
     * @param newManager The manager to be added.
     */
    public void addUser(HdbManager newManager) {
        this.managers.put(newManager.getId(), newManager);
    }

    /**
     * Retrieves a manager by their unique ID.
     * @param managerId The ID of the manager.
     * @return The manager with the given ID, or null if not found.
     */
    public HdbManager getUser(String managerId) {
        return managers.get(managerId);
    }

    /**
     * Gets all managers in the repository.
     * @return A map of manager IDs to managers.
     */
    public HashMap<String, HdbManager> getManagers() {
        return managers;
    }
}
