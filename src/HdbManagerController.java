import java.util.*;
import java.io.*;

public class HdbManagerController{
    private static final String filePath = "Resource\\ManagerList.csv";
    private HashMap<String, HdbManager> managers;

    public HdbManagerController() {
        this.managers = new HashMap<String, HdbManager>();
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
                    User.MaritalStatus maritalStatus = User.MaritalStatus.valueOf(values[3].trim().toUpperCase());

                    addHdbManager(name, nric, age, maritalStatus, password);

                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveHdbManagers(){
        File file = new File(filePath);
        try {
            // First truncate the file (clear all contents)
            new FileOutputStream(file).close();

            // Then rewrite the file including any new managers and changes made
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                // Write the header line
                bw.write("Name,NRIC,Age,Marital Status,Password,ManagerId");
                bw.newLine();

                // Write each applicant's data
                for (HdbManager managerPerson : managers.values()) {
                    String line = String.join(",",
                            managerPerson.getName(),
                            managerPerson.getNric(),
                            String.valueOf(managerPerson.getAge()),
                            managerPerson.getMaritalStatus().toString(),
                            managerPerson.getPassword(),
                            managerPerson.getId()
                    );
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void addHdbManager(String name, String nric, int age, User.MaritalStatus maritalStatus, String password){
        HdbManager newManager  = new HdbManager(name, nric, age, maritalStatus, password);
        this.managers.put(newManager.getId(), newManager);
    }

    public HdbManager getHdbManager(String managerId){
        return managers.get(managerId);
    }

    private boolean check(HdbManager manager) {
        if(manager == null) {
            System.out.println("No such manager!!");
            return false;
        }
        return true;
    }

    public void createProjectListing(String managerId, String projectId, String name, String neighbourhood, HashMap<String,Integer> flatTypes,
                                        Date applicationOpenDate, Date applicationCloseDate, int officerSlots) {
        HdbManager manager = managers.get(managerId);
        if(!check(manager)) return;
        manager.createProjectListing(projectId, name, neighbourhood, flatTypes, applicationOpenDate, applicationCloseDate, officerSlots);
    }
    // 

    public void editProjectListing(String managerId, String projectId, String name, String neighbourhood,
                                    Date applicationOpenDate,Date applicationCloseDate) {
        HdbManager manager = managers.get(managerId);
        if(!check(manager)) return;
        manager.editProjectListing(projectId, name, neighbourhood, applicationOpenDate, applicationCloseDate);
    }

    public void deleteProjectListing(String managerId, String projectId) {
        HdbManager manager = managers.get(managerId);
        if(!check(manager)) return;
        manager.deleteProjectListing(projectId);
    }

    public void toggleProjectVisibility(String managerId, String projectId) {
        HdbManager manager = managers.get(managerId);
        if(!check(manager)) return;
        manager.toggleProjectVisibility(projectId);
    }

    public void viewCreatedProjects(String managerId) {
        HdbManager manager = managers.get(managerId);
        if(!check(manager)) return;
        manager.viewCreatedProjects();
    }

    public void viewPendingOfficers() {
        HdbOfficerController.getPendingOfficers().forEach(System.out::println);
    }

    public void viewApprovedOfficers() {
        HdbOfficerController.getApprovedOfficers().forEach(System.out::println);
    }

    public boolean approveOfficerApplication(String managerId, String officerId) {
        HdbManager manager = managers.get(managerId);
        if(!check(manager)) return false;
        return manager.approveOfficerApplication(officerId);
    }

    public boolean approveApplicantBTOApplication(String managerId, String applicationId) {
        HdbManager manager = managers.get(managerId);
        if(!check(manager)) return false;
        return manager.approveApplicantBTOApplication(applicationId);
    }

    public boolean approveApplicantWithdrawal(String managerId, String applicationId) {
        HdbManager manager = managers.get(managerId);
        if(!check(manager)) return false;
        return manager.approveApplicantWithdrawal(applicationId);
    }

    // public void viewEnquiry() {
    //     ProjectEnquiryController.getAllEnquiries()
    //             .forEach(System.out::println);
    // }

    public void replyEnquiry(String managerId, String enquiryId, String response) {
        HdbManager manager = managers.get(managerId);
        if(!check(manager)) return;
        manager.replyEnquiry(enquiryId, response);
    }

    public boolean isDuringApplicationPeriod(String managerId, Date date) {
        HdbManager manager = managers.get(managerId);
        if(!check(manager)) return true;
        return manager.isDuringApplicationPeriod(date);
    }

    public boolean isManaging(String managerId, String projectId) {
        HdbManager manager = managers.get(managerId);
        if(!check(manager)) return false;
        return manager.isManaging(projectId);
    }
}