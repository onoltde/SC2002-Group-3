import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class ApplicantRepo {
    private static final String applicantFilePath = "data\\ApplicantList.csv";
    private static HashMap<String, Applicant> applicants;
    private static int numOfApplicants;
    private static HashMap<String,Application> applications;

    public ApplicantRepo() {
        numOfApplicants = 0;
        applicants = new HashMap<>();
        applications = new HashMap<>();
        loadFile();
    }

    private void loadFile() {
        File applicantList = new File(applicantFilePath);
        try (BufferedReader br = new BufferedReader(new FileReader(applicantList))) {
            // Skip the header line
            br.readLine();

            String line = br.readLine();
            while (line != null) {
                String[] values = line.split(",");

                if (values.length >= 4) {
                    String name = values[0].trim();
                    String nric = values[1].trim();
                    int age = Integer.parseInt(values[2].trim());
                    Applicant.MaritalStatus maritalStatus = Applicant.MaritalStatus.valueOf(values[3].trim().toUpperCase());
                    String password = values[4].trim();
                    String applicantID = "AP-" + nric.substring(5);
                    Application application = null;
                    if(values.length == 9){     //applicant has application
                        Application.Status status = Application.Status.valueOf(values[6].trim().toUpperCase());
                        String projectName = values[7].trim();
                        Flat.Type flatType = Flat.Type.valueOf(values[8].trim().toUpperCase());
                        application = addApplication(applicantID, status, projectName, flatType);
                    }
                    addApplicant(name, nric, age, maritalStatus, password, application);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void saveApplicants(){
        File file = new File(applicantFilePath);
        try {
            // First truncate the file (clear all contents)
            new FileOutputStream(file).close();

            // Then rewrite the file including any new Applicants and changes made
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                // Write the header line
                bw.write("Name,NRIC,Age,MaritalStatus,Password,ApplicantId,ApplicationStatus,ProjectName,FlatType");
                bw.newLine();

                // Write each applicant's data
                for (Applicant applicant : applicants.values()) {
                    if (applicant.getApplication() == null){
                        String line = String.join(",",
                                applicant.getName(),
                                applicant.getNric(),
                                String.valueOf(applicant.getAge()),
                                applicant.getMaritalStatus().toString(),
                                applicant.getPassword(),
                                applicant.getId());
                        bw.write(line);
                        bw.newLine();
                    }else{
                        String line = String.join(",",
                                applicant.getName(),
                                applicant.getNric(),
                                String.valueOf(applicant.getAge()),
                                applicant.getMaritalStatus().toString(),
                                applicant.getPassword(),
                                applicant.getId(),
                                applicant.getApplication().getStatus().toString(),
                                applicant.getApplication().getProjectName(),
                                applicant.getApplication().getFlatType().toString());
                        bw.write(line);
                        bw.newLine();
                    }



                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    public static void printApplicants(){
        for (Applicant applicant : applicants.values()){
            System.out.println(applicant.toString());
        }
    }

    public static void printApplications(){
        for (Application applications : applications.values()){
            System.out.println(applications.toString());
        }
    }
    public static Application addApplication( String applicantId, Application.Status status, String projectName, Flat.Type flatType){
        Application application = new Application(applicantId,status,projectName,flatType);
        applications.put(projectName,application);
        return application;
    }

    public static void addApplicant(String name, String nric, int age, User.MaritalStatus maritalStatus, String password, Application application){
        Applicant newApplicant = new Applicant(name, nric, age, maritalStatus, password, application);
        applicants.put(newApplicant.getId(), newApplicant);
        numOfApplicants += 1;
    }

    public static Applicant getApplicant(String applicantId){
        return applicants.get(applicantId);
    }

    public static int getNumOfApplicants(){ return numOfApplicants;}

}