import java.io.*;
import java.util.*;

public class ApplicantRepo implements UserRepo<Applicant>{
    private static final String applicantFilePath = "data\\ApplicantList.csv";
    private static HashMap<String, Applicant> applicants;
    private static int numOfApplicants;
    private ApplicationRepo applicationRepo;

    public ApplicantRepo(ApplicationRepo applicationRepo) {
        numOfApplicants = 0;
        applicants = new HashMap<String,Applicant>();
        this.applicationRepo = applicationRepo;
        loadFile();
    }

    public void loadFile() {
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
                    String applicantID = generateID(nric);
                    Application application = null;
                    if(values.length == 9){     //applicant has application
                        Application.Status status = Application.Status.valueOf(values[6].trim().toUpperCase());
                        String projectName = values[7].trim();
                        Flat.Type flatType = Flat.Type.valueOf(values[8].trim().toUpperCase());
                        application = applicationRepo.addApplication(applicantID, status, projectName, flatType);
                    }
                    Applicant newApplicant = new Applicant(name, nric, age, maritalStatus, password, application);
                    addUser(newApplicant);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }



    public void saveFile(){
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

    public String generateID(String nric){
        return "AP-" + nric.substring(5);
    }

    public void addUser(Applicant newApplicant){
        applicants.put(newApplicant.getId(), newApplicant);
        numOfApplicants += 1;
    }

    public Applicant getUser(String applicantId){
        return applicants.get(applicantId);
    }

    //for debugging
    public void printApplicants(){
        for (Applicant applicant : applicants.values()){
            System.out.println(applicant.toString());
        }
    }

    public int getNumOfApplicants(){ return numOfApplicants;}

}