import java.io.*;
import java.util.*;

public class ApplicantManager {

    // In-memory list of applicants
    private HashMap<String, Applicant> applicants;

    public ApplicantManager() {
        this.applicants = new HashMap<String, Applicant>();
    }

    public void loadFile() {
        this.applicants.clear();    //makes sure current applicants is empty

        String filePath = "SC2002\\data\\ApplicantList.csv";
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
                    Applicant.MaritalStatus maritalStatus = Applicant.MaritalStatus.valueOf(values[3].trim());

                    Applicant applicant = new Applicant(name, nric, age, maritalStatus, password);
                    applicants.put(applicant.getId(), applicant);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing age: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error parsing marital status: " + e.getMessage());
        }
    }

    public Applicant getApplicant(String applicantId){
        return applicants.get(applicantId);
    }

}