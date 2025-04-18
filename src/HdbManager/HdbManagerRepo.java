package HdbManager;
import Project.ProjectController;
import Project.ProjectControllerInterface;
import Users.*;
import java.util.*;
import java.io.*;

public class HdbManagerRepo implements UserRepo <HdbManager>{
    private static final String filePath = "data\\ManagerList.csv";
    private ProjectControllerInterface projectController;
    private HashMap<String, HdbManager> managers;

    public HdbManagerRepo(ProjectControllerInterface projectController) {
        this.managers = new HashMap<String, HdbManager>();
        this.projectController = projectController;
        loadFile();
    }

    public void loadFile() {
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Skip the header line
            br.readLine();

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
                    HdbManager newManager  = new HdbManager(name, nric, age, maritalStatus, password,
                            (projectName == "null" ? null : projectController.getRepo().getProject(projectName)));
                    addUser(newManager);

                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveFile(){
        File file = new File(filePath);
        try {
            // First truncate the file (clear all contents)
            new FileOutputStream(file).close();

            // Then rewrite the file including any new managers and changes made
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                // Write the header line
                bw.write("Name,NRIC,Age,Marital Status,Password,ManagerId,AssignedProjectName");
                bw.newLine();

                // Write each applicant's data
                for (HdbManager managerPerson : managers.values()) {
                    String line = String.join(",",
                            managerPerson.getName(),
                            managerPerson.getNric(),
                            String.valueOf(managerPerson.getAge()),
                            managerPerson.getMaritalStatus().toString(),
                            managerPerson.getPassword(),
                            managerPerson.getId(),
                            (managerPerson.getManagedProject() == null ? "null" :
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

    public String generateID(String nric) {
        return "MA-" + nric.substring(5);
    }

    public void addUser(HdbManager newManager){
        this.managers.put(newManager.getId(), newManager);
    }

    public HdbManager getUser(String managerId){
        return managers.get(managerId) ;
    }

    public HashMap<String,HdbManager> getManagers(){
        return managers;
    }


}