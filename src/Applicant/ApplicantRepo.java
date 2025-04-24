package Applicant;

import Users.*;
import Application.Residential.*;
import Project.*;
import java.io.*;
import java.util.*;

/**
 * The ApplicantRepo class manages the persistence and retrieval of applicant data.
 * It handles the loading and saving of applicants' data to a file and provides methods
 * to add, retrieve, and generate unique IDs for applicants.
 */
public class ApplicantRepo implements UserRepo<Applicant> {

    private static final String applicantFilePath = "data\\ApplicantList.csv"; // File path for applicant data
    private static HashMap<String, Applicant> applicants; // Stores applicants indexed by their unique ID
    private ResidentialApplicationRepo applicationRepo; // Repository for residential applications

    /**
     * Constructor for ApplicantRepo.
     * Initializes the applicant repository and loads data from the file.
     *
     * @param applicationRepo The repository for managing residential applications.
     */
    public ApplicantRepo(ResidentialApplicationRepo applicationRepo) {
        applicants = new HashMap<String, Applicant>();
        this.applicationRepo = applicationRepo;
        loadFile(); // Load applicants from file into memory
    }

    /**
     * Loads the applicant data from the CSV file.
     * It reads the data line by line and constructs Applicant objects based on the file's contents.
     * Each applicant's information, including any associated residential application, is stored.
     */
    public void loadFile() {
        File applicantList = new File(applicantFilePath);
        try (BufferedReader br = new BufferedReader(new FileReader(applicantList))) {
            // Skip the header line
            br.readLine();

            String line = br.readLine();
            while (line != null) {
                String[] values = line.split(",");

                if (values.length >= 7) { // Ensure the applicant has essential information
                    String name = values[0].trim();
                    String nric = values[1].trim();
                    int age = Integer.parseInt(values[2].trim());
                    Applicant.MaritalStatus maritalStatus = Applicant.MaritalStatus.valueOf(values[3].trim().toUpperCase());
                    String password = values[4].trim();
                    String applicantID = generateID(nric);

                    // Check if the applicant has a residential application
                    boolean hasApplication = Boolean.parseBoolean(values[6].trim().toLowerCase());
                    ResidentialApplication residentialApplication = null;
                    if (hasApplication) {
                        ResidentialApplication.Status status = ResidentialApplication.Status.valueOf(values[7].trim().toUpperCase());
                        String projectName = values[8].trim();
                        Flat.Type flatType = Flat.Type.valueOf(values[9].trim().toUpperCase());
                        residentialApplication = new ResidentialApplication(applicantID, status, projectName, flatType);
                        applicationRepo.addApplication(residentialApplication); // Add the application to the repo
                    }

                    // Create a new applicant object and add it to the repository
                    Applicant newApplicant = new Applicant(name, nric, age, maritalStatus, password, residentialApplication);
                    addUser(newApplicant);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Saves the current state of the applicant repository to the CSV file.
     * It overwrites the file with the latest data, including all applicants and their residential applications.
     */
    public void saveFile() {
        File file = new File(applicantFilePath);
        try {
            // First truncate the file (clear all contents)
            new FileOutputStream(file).close();

            // Then rewrite the file with updated data
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

                    if (applicant.hasResidentialApplication()) { // If the applicant has a residential application
                        sb.append(",")
                                .append(applicant.getResidentialApplication().getStatus())
                                .append(",")
                                .append(applicant.getResidentialApplication().getProjectName())
                                .append(",")
                                .append(applicant.getResidentialApplication().getFlatType());
                    }

                    // Write the constructed string to the file
                    bw.write(sb.toString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Adds a new applicant to the repository.
     *
     * @param newApplicant The applicant to be added.
     */
    public void addUser(Applicant newApplicant) {
        applicants.put(newApplicant.getId(), newApplicant); // Store the applicant using their ID as the key
    }

    /**
     * Retrieves an applicant by their unique ID.
     *
     * @param applicantId The ID of the applicant to retrieve.
     * @return The applicant object, or null if the applicant is not found.
     */
    public Applicant getUser(String applicantId) {
        return applicants.get(applicantId); // Retrieve the applicant by ID
    }

    /**
     * Generates a unique applicant ID based on the applicant's NRIC.
     * The ID is derived by extracting a substring from the NRIC.
     *
     * @param nric The NRIC of the applicant.
     * @return The generated applicant ID.
     */
    public String generateID(String nric) {
        return "AP-" + nric.substring(5); // The applicant ID starts with "AP-" followed by a substring of the NRIC
    }
}