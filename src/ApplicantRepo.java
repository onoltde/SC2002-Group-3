import java.io.*;
import java.util.*;

public class ApplicantRepo {
    private static final String filePath = "data\\ApplicantList.csv";
    private HashMap<String, Applicant> applicants;
    private static int numOfApplicants;

    public ApplicantRepo() {
        numOfApplicants = 0;
        this.applicants = new HashMap<String, Applicant>();
        loadFile();
    }

    private void loadFile() {
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
                    Applicant.MaritalStatus maritalStatus = Applicant.MaritalStatus.valueOf(values[3].trim().toUpperCase());
                    addApplicant(name, nric, age, maritalStatus, password);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveApplicants(){
        File file = new File(filePath);
        try {
            // First truncate the file (clear all contents)
            new FileOutputStream(file).close();

            // Then rewrite the file including any new Applicants and changes made
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                // Write the header line
                bw.write("Name,NRIC,Age,Marital Status,Password,ApplicantId");
                bw.newLine();

                // Write each applicant's data
                for (Applicant applicant : applicants.values()) {
                    String line = String.join(",",
                            applicant.getName(),
                            applicant.getNric(),
                            String.valueOf(applicant.getAge()),
                            applicant.getMaritalStatus().toString(),
                            applicant.getPassword(),
                            applicant.getId()
                    );
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    public void printApplicants(){
        for (Applicant applicant : applicants.values()){
            System.out.println(applicant.toString());
        }
    }

    public void addApplicant(String name, String nric, int age, User.MaritalStatus maritalStatus, String password){
        Applicant newApplicant = new Applicant(name, nric, age, maritalStatus, password);
        this.applicants.put(newApplicant.getId(), newApplicant);
        numOfApplicants += 1;
    }

    public Applicant getApplicant(String applicantId){
        return applicants.get(applicantId);
    }

    public int getNumOfApplicants(){ return numOfApplicants;}
}