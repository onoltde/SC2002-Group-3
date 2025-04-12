package HdbOfficer;
import java.util.*;
import Application.Team.*;
import Application.Residential.*;
import HdbManager.HdbManager;
import Users.*;
import Project.*;
import java.io.*;

public class HdbOfficerRepo implements UserRepo <HdbOfficer> {
    private static final String filePath = "data\\OfficerList.csv";
    private static HashMap<String, HdbOfficer> officers;
    private ResidentialApplicationRepo resAppRepo;
    private TeamApplicationRepo teamAppRepo;

    public HdbOfficerRepo(ResidentialApplicationRepo resAppRepo, TeamApplicationRepo teamAppRepo) {
        officers = new HashMap<String, HdbOfficer>();
        this.resAppRepo = resAppRepo;
        this.teamAppRepo = teamAppRepo;
        loadFile();
    }

    public void loadFile() {
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Skip the header line
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                // Split on commas NOT inside quotes, then trim quotes
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Clean each value by removing surrounding quotes
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("^\"|\"$", "").trim();
                }

                if (values.length >= 6) {
                    //officer has essential information
                    String name = values[0].trim();
                    String nric = values[1].trim();
                    int age = Integer.parseInt(values[2].trim());
                    User.MaritalStatus maritalStatus = User.MaritalStatus.valueOf(values[3].trim().toUpperCase());
                    String password = values[4].trim();
                    String officerID = generateID(nric);  //index 5 is id
                    boolean hasResApplication = Boolean.parseBoolean(values[6].trim().toLowerCase());
                    ResidentialApplication residentialApplication = null;

                    //residentialApplication portion
                    if(hasResApplication){
                        ResidentialApplication.Status status = ResidentialApplication.Status.valueOf(values[7].trim().toUpperCase());
                        String projectName = values[8].trim();
                        Flat.Type flatType = Flat.Type.valueOf(values[9].trim().toUpperCase());
                        residentialApplication = new ResidentialApplication(officerID,status,projectName,flatType);
                        resAppRepo.addApplication(residentialApplication);
                    }
                    ArrayList<String> blacklist = stringToList(values[10]);

                    //assignedProject portion
                    boolean hasAssignedProject = Boolean.parseBoolean(values[11].trim().toLowerCase());
                    String assignedProjectName = "";

                    if(hasAssignedProject){
                        assignedProjectName = values[12].trim();
                    }

                    //Team application portion
                    boolean hasTeamApplication = Boolean.parseBoolean(values[13].trim().toLowerCase());
                    TeamApplication teamApplication = null;
                    if (hasTeamApplication){
                        String appliedTeamName = values[14].trim();
                        TeamApplication.Status status = TeamApplication.Status.valueOf(values[15].trim().toUpperCase());
                        teamApplication = new TeamApplication(officerID, appliedTeamName, status);
                        teamAppRepo.addApplication(teamApplication);
                    }
                    HdbOfficer newOfficer  = new HdbOfficer(name, nric, age, maritalStatus, password, residentialApplication, blacklist, hasAssignedProject, assignedProjectName, hasTeamApplication, teamApplication );
                    addUser(newOfficer);    //adds to repo

                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveFile(){ //BROKEN NEEDS DEBUGGING ON WRITING BLACKLIST BACK INTO CSV
        File file = new File(filePath);
        try {
            // First truncate the file (clear all contents)
            new FileOutputStream(file).close();

            // Then rewrite the file including any new officers and changes made
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                // Write the header line
                bw.write("Name,NRIC,Age,Marital Status,Password,OfficerID,Has Residential Application?,Residential Application Status,Residential Project Name,Flat Type,Blacklist,Has Assigned Project,Assigned Project Name,Has Team Application,Team Application,Team Application Status");
                bw.newLine();

                // Write each officerPerson's data
                for (HdbOfficer officerPerson : officers.values()) {

                    StringBuilder sb = new StringBuilder()
                            .append(officerPerson.getName()).append(",")
                            .append(officerPerson.getNric()).append(",")
                            .append(String.valueOf(officerPerson.getAge())).append(",")
                            .append(officerPerson.getMaritalStatus().toString()).append(",")
                            .append(officerPerson.getPassword()).append(",")
                            .append(officerPerson.getId()).append(",")
                            .append(Boolean.toString(officerPerson.hasResidentialApplication()));

                    if (officerPerson.hasResidentialApplication()) {    //has residential application
                        sb.append(",")
                                .append(officerPerson.getResidentialApplication().getStatus())
                                .append(",")
                                .append(officerPerson.getResidentialApplication().getProjectName())
                                .append(",")
                                .append(officerPerson.getResidentialApplication().getFlatType());
                    }else{
                        sb.append(",")
                                .append(",")
                                .append(",");
                    }
                    sb.append(",")
                            .append(listToString(officerPerson.getBlacklist()))
                            .append(",")
                            .append(officerPerson.hasAssignedProject());

                    if(officerPerson.hasAssignedProject()){
                        sb.append(",")
                        .append(officerPerson.getAssignedProjectName())
                                .append(",")
                                .append(officerPerson.hasTeamApplication());
                    }else{
                        sb.append(",")
                                .append(",")
                        .append(officerPerson.hasTeamApplication());
                    }

                    if(officerPerson.hasTeamApplication()){
                        sb.append(",")
                                .append(officerPerson.getTeamApplication().getProjectName())
                                .append(",")
                                .append(officerPerson.getTeamApplication().getStatus());
                    }else{
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

    public void addUser(HdbOfficer newOfficer){
        officers.put(newOfficer.getId(), newOfficer);
    }

    public HdbOfficer getUser(String officerId){
        return officers.get(officerId) ;
    }

    public  String generateID(String nric){
        return "OF-" + nric.substring(5);
    }

    //helper functions
    public static ArrayList<String> stringToList(String input) {
        // Remove surrounding quotes if they exist
        String content = input;
        if (input.startsWith("\"") && input.endsWith("\"")) {
            content = input.substring(1, input.length() - 1);
        }

        // Split by comma and trim whitespace from each element
        ArrayList<String> list = new ArrayList<>(Arrays.asList(content.split("\\s*,\\s*")));
        return list;
    }

    public static String listToString(ArrayList<String> list) {
        // Join elements with ", " delimiter
        String joined = String.join(", ", list);
        // Add surrounding quotes
        return "\"" + joined + "\"";
    }

    public HashMap<String, HdbOfficer> getOfficers(){
        return officers;
    }

}





