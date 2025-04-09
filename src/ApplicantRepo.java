import java.io.*;
import java.util.*;

public class ApplicantRepo implements UserRepo<Applicant>{
    private static final String applicantFilePath = "data\\ApplicantList.csv";
    private static HashMap<String, Applicant> applicants;
    private static int numOfApplicants;
    private ResidentialApplicationRepo applicationRepo;

    public ApplicantRepo(ResidentialApplicationRepo applicationRepo) {
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

                if (values.length >= 7) //applicant has essential information
                {
                    String name = values[0].trim();
                    String nric = values[1].trim();
                    int age = Integer.parseInt(values[2].trim());
                    Applicant.MaritalStatus maritalStatus = Applicant.MaritalStatus.valueOf(values[3].trim().toUpperCase());
                    String password = values[4].trim();
                    String applicantID = generateID(nric);

                    //application portion of csv file
                    boolean hasApplication = Boolean.parseBoolean(values[6].trim().toLowerCase());
                    ResidentialApplication residentialApplication = null;
                    if(hasApplication){     //applicant has residentialApplication
                        ResidentialApplication.Status status = ResidentialApplication.Status.valueOf(values[7].trim().toUpperCase());
                        String projectName = values[8].trim();
                        Flat.Type flatType = Flat.Type.valueOf(values[9].trim().toUpperCase());
                        residentialApplication = new ResidentialApplication(applicantID,status,projectName,flatType);
                        applicationRepo.addApplication(residentialApplication);
                    }

                    Applicant newApplicant = new Applicant(name, nric, age, maritalStatus, password, residentialApplication);
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
                bw.write("Name,NRIC,Age,MaritalStatus,Password,ApplicantID,Has Application?,Application Status,Project Name,Flat Type");
                bw.newLine();

                // Write each applicant's data
                for (Applicant applicant : applicants.values()) {

                    StringBuilder sb = new StringBuilder()
                            .append(applicant.getName()).append(",")
                            .append(applicant.getNric()).append(",")
                            .append(String.valueOf(applicant.getAge())).append(",")
                            .append(applicant.getMaritalStatus().toString()).append(",")
                            .append(applicant.getPassword()).append(",")
                            .append(applicant.getId()).append(",")
                            .append(Boolean.toString(applicant.hasResidentialApplication()));

                    if (applicant.hasResidentialApplication()) {    //has residential application
                        sb.append(",")
                                .append(applicant.getResidentialApplication().getStatus())
                                .append(",")
                                .append(applicant.getResidentialApplication().getProjectName())
                                .append(",")
                                .append(applicant.getResidentialApplication().getFlatType());
                    }

                    bw.write(sb.toString());
                    bw.newLine();

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