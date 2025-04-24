package Applicant;

import Users.*;
import Application.Residential.*;
import Project.*;
import java.io.*;
import java.util.*;

/**
 * Repository class responsible for managing storage, retrieval, and persistence
 * of Applicant objects. Handles file I/O operations for reading and writing applicant data.
 */
public class ApplicantRepo implements UserRepo<Applicant> {

    /** Path to the CSV file storing applicant data. */
    private static final String applicantFilePath = "data\\ApplicantList.csv";

    /** In-memory storage of applicants, keyed by their unique applicant ID. */
    private static HashMap<String, Applicant> applicants;

    /** Repository for residential applications, used to link applications with applicants. */
    private ResidentialApplicationRepo applicationRepo;

    /**
     * Constructs an ApplicantRepo and loads applicant data from file.
     *
     * @param applicationRepo The residential application repository
     */
    public ApplicantRepo(ResidentialApplicationRepo applicationRepo) {
        applicants = new HashMap<String, Applicant>();
        this.applicationRepo = applicationRepo;
        loadFile();
    }

    /**
     * Loads applicant data from a CSV file. Each line in the file represents
     * an applicant, optionally with a linked residential application.
     */
    public void loadFile() {
        File applicantList = new File(applicantFilePath);
        try (BufferedReader br = new BufferedReader(new FileReader(applicantList))) {
            // Skip the header line
            br.readLine();

            String line = br.readLine();
            while (line != null) {
                String[] values = line.split(",");

                if (values.length >= 7) { // applicant has essential information
                    String name = values[0].trim();
                    String nric = values[1].trim();
                    int age = Integer.parseInt(values[2].trim());
                    Applicant.MaritalStatus maritalStatus = Applicant.MaritalStatus.valueOf(values[3].trim().toUpperCase());
                    String password = values[4].trim();
                    String applicantID = generateID(nric);

                    // Application portion of CSV file
                    boolean hasApplication = Boolean.parseBoolean(values[6].trim().toLowerCase());
                    ResidentialApplication residentialApplication = null;
                    if (hasApplication) {
                        ResidentialApplication.Status status = ResidentialApplication.Status.valueOf(values[7].trim().toUpperCase());
                        String projectName = values[8].trim();
                        Flat.Type flatType = Flat.Type.valueOf(values[9].trim().toUpperCase());
                        residentialApplication = new ResidentialApplication(applicantID, status, projectName, flatType);
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

    /**
     * Saves all applicant data to the CSV file. Overwrites the existing file content.
     */
    public void saveFile() {
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

                    if (applicant.hasResidentialApplication()) {
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

    /**
     * Adds a new applicant to the repository.
     *
     * @param newApplicant The applicant to be added
     */
    public void addUser(Applicant newApplicant) {
        applicants.put(newApplicant.getId(), newApplicant);
    }

    /**
     * Retrieves an applicant by their ID.
     *
     * @param applicantId The unique ID of the applicant
     * @return The corresponding Applicant object, or null if not found
     */
    public Applicant getUser(String applicantId) {
        return applicants.get(applicantId);
    }

    /**
     * Helper function to generate an applicant ID from a given NRIC.
     *
     * @param nric The NRIC number
     * @return A unique applicant ID in the format "AP-xxxxx"
     */
    public String generateID(String nric) {
        return "AP-" + nric.substring(5);
    }

}
