package HdbOfficer;

import java.util.*;
import Application.Team.*;
import Application.Residential.*;
import HdbManager.HdbManager;
import Users.*;
import Project.*;
import java.io.*;

/**
 * Repository class to manage HDB Officers.
 * Handles loading and saving officer data from/to a CSV file, and provides methods to interact with officer records.
 */
public class HdbOfficerRepo implements UserRepo<HdbOfficer> {

    private static final String filePath = "data\\OfficerList.csv";  // Path to the CSV file storing officer data
    private static HashMap<String, HdbOfficer> officers;  // HashMap storing officers indexed by their ID
    private ResidentialApplicationRepo resAppRepo;  // Repository for residential applications
    private TeamApplicationRepo teamAppRepo;  // Repository for team applications

    /**
     * Constructs an HdbOfficerRepo with the specified residential and team application repositories.
     *
     * @param resAppRepo The repository for residential applications.
     * @param teamAppRepo The repository for team applications.
     */
    public HdbOfficerRepo(ResidentialApplicationRepo resAppRepo, TeamApplicationRepo teamAppRepo) {
        officers = new HashMap<String, HdbOfficer>();
        this.resAppRepo = resAppRepo;
        this.teamAppRepo = teamAppRepo;
        loadFile();  // Load the officer data from the CSV file
    }

    /**
     * Loads officer data from a CSV file and populates the repository.
     */
    public void loadFile() {
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Skip the header line
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                // Split on commas, handling quoted values
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Clean each value by removing surrounding quotes
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("^\"|\"$", "").trim();
                }

                if (values.length >= 6) {
                    // Extract essential officer information
                    String name = values[0].trim();
                    String nric = values[1].trim();
                    int age = Integer.parseInt(values[2].trim());
                    User.MaritalStatus maritalStatus = User.MaritalStatus.valueOf(values[3].trim().toUpperCase());
                    String password = values[4].trim();
                    String officerID = generateID(nric);  // Generate officer ID based on NRIC
                    boolean hasResApplication = Boolean.parseBoolean(values[6].trim().toLowerCase());
                    ResidentialApplication residentialApplication = null;

                    // Handle residential application data
                    if (hasResApplication) {
                        ResidentialApplication.Status status = ResidentialApplication.Status.valueOf(values[7].trim().toUpperCase());
                        String projectName = values[8].trim();
                        Flat.Type flatType = Flat.Type.valueOf(values[9].trim().toUpperCase());
                        residentialApplication = new ResidentialApplication(officerID, status, projectName, flatType);
                        resAppRepo.addApplication(residentialApplication);
                    }

                    ArrayList<String> blacklist = stringToList(values[10]);  // Convert blacklist string to list

                    // Handle assigned project data
                    boolean hasAssignedProject = Boolean.parseBoolean(values[11].trim().toLowerCase());
                    String assignedProjectName = "";
                    if (hasAssignedProject) {
                        assignedProjectName = values[12].trim();
                    }

                    // Handle team application data
                    boolean hasTeamApplication = Boolean.parseBoolean(values[13].trim().toLowerCase());
                    TeamApplication teamApplication = null;
                    if (hasTeamApplication) {
                        String appliedTeamName = values[14].trim();
                        TeamApplication.Status status = TeamApplication.Status.valueOf(values[15].trim().toUpperCase());
                        teamApplication = new TeamApplication(officerID, appliedTeamName, status);
                        teamAppRepo.addApplication(teamApplication);
                    }

                    // Create new officer object and add to repository
                    HdbOfficer newOfficer = new HdbOfficer(name, nric, age, maritalStatus, password, residentialApplication, blacklist, hasAssignedProject, assignedProjectName, hasTeamApplication, teamApplication);
                    addUser(newOfficer);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Saves all officer data back to the CSV file.
     */
    public void saveFile() {
        File file = new File(filePath);
        try {
            // First truncate the file (clear all contents)
            new FileOutputStream(file).close();

            // Then rewrite the file including any new officers and changes made
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                // Write the header line
                bw.write("Name,NRIC,Age,Marital Status,Password,OfficerID,Has Residential Application?,Residential Application Status,Residential Project Name,Flat Type,Blacklist,Has Assigned Project,Assigned Project Name,Has Team Application,Team Application,Team Application Status");
                bw.newLine();

                // Write each officer's data
                for (HdbOfficer officerPerson : officers.values()) {
                    StringBuilder sb = new StringBuilder()
                            .append(officerPerson.getName()).append(",")
                            .append(officerPerson.getNric()).append(",")
                            .append(String.valueOf(officerPerson.getAge())).append(",")
                            .append(officerPerson.getMaritalStatus().toString()).append(",")
                            .append(officerPerson.getPassword()).append(",")
                            .append(officerPerson.getId()).append(",")
                            .append(Boolean.toString(officerPerson.hasResidentialApplication()));

                    // Handle residential application data
                    if (officerPerson.hasResidentialApplication()) {
                        sb.append(",")
                                .append(officerPerson.getResidentialApplication().getStatus())
                                .append(",")
                                .append(officerPerson.getResidentialApplication().getProjectName())
                                .append(",")
                                .append(officerPerson.getResidentialApplication().getFlatType());
                    } else {
                        sb.append(",")
                                .append(",")
                                .append(",");
                    }

                    // Handle blacklist data
                    sb.append(",")
                            .append(listToString(officerPerson.getBlacklist()))
                            .append(",")
                            .append(officerPerson.hasAssignedProject());

                    // Handle assigned project data
                    if (officerPerson.hasAssignedProject()) {
                        sb.append(",")
                                .append(officerPerson.getAssignedProjectName())
                                .append(",")
                                .append(officerPerson.hasTeamApplication());
                    } else {
                        sb.append(",")
                                .append(",")
                                .append(officerPerson.hasTeamApplication());
                    }

                    // Handle team application data
                    if (officerPerson.hasTeamApplication()) {
                        sb.append(",")
                                .append(officerPerson.getTeamApplication().getProjectName())
                                .append(",")
                                .append(officerPerson.getTeamApplication().getStatus());
                    } else {
                        sb.append(",")
                                .append(",");
                    }

                    bw.write(sb.toString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Adds a new HDB Officer to the repository.
     *
     * @param newOfficer The new officer to add to the repository.
     */
    public void addUser(HdbOfficer newOfficer) {
        officers.put(newOfficer.getId(), newOfficer);
    }

    /**
     * Retrieves an officer from the repository by their ID.
     *
     * @param officerId The ID of the officer to retrieve.
     * @return The HdbOfficer object, or null if not found.
     */
    public HdbOfficer getUser(String officerId) {
        return officers.get(officerId);
    }

    /**
     * Generates a unique officer ID based on the NRIC.
     *
     * @param nric The NRIC of the officer.
     * @return The generated officer ID.
     */
    public String generateID(String nric) {
        return "OF-" + nric.substring(5);
    }

    // Helper methods to convert lists to and from CSV-compatible strings

    /**
     * Converts a comma-separated string to an ArrayList of strings.
     *
     * @param input The comma-separated string.
     * @return The resulting ArrayList of strings.
     */
    public static ArrayList<String> stringToList(String input) {
        String content = input;
        ArrayList<String> list;
        if (input.startsWith("\"") && input.endsWith("\"")) {
            content = input.substring(1, input.length() - 1);
        }

        if (!content.isBlank()) {
            list = new ArrayList<>(Arrays.asList(content.split("\\s*,\\s*")));
        } else {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * Converts an ArrayList of strings to a CSV-compatible string.
     *
     * @param list The list of strings.
     * @return The resulting CSV string.
     */
    public static String listToString(ArrayList<String> list) {
        String joined = String.join(", ", list);
        if (list.size() > 1) {
            return "\"" + joined + "\"";
        } else {
            return joined;
        }
    }

    /**
     * Gets the officers in the repository.
     *
     * @return The HashMap of officers, indexed by their officer ID.
     */
    public HashMap<String, HdbOfficer> getOfficers() {
        return officers;
    }
}