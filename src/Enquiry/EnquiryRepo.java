package Enquiry;

import java.io.*;
import java.util.*;

/**
 * Repository class responsible for managing the storage and retrieval of enquiry data.
 * It handles loading and saving enquiries from/to a CSV file, as well as querying enquiries by different criteria.
 */
public class EnquiryRepo {
    private static final String filePath = "data\\EnquiryList.csv";  // Path to the CSV file where enquiries are stored
    private static int counter = 0;  // Counter to generate unique enquiry IDs
    private HashMap<String, Enquiry> enquiries;  // Map to store enquiries by their ID

    /**
     * Constructs an EnquiryRepo instance and loads existing data from the file.
     */
    public EnquiryRepo() {
        enquiries = new HashMap<>();
        loadFile();  // Load data from file during initialization
    }

    /**
     * Generates a unique ID for a new enquiry.
     *
     * @return A unique enquiry ID in the format "EN######"
     */
    public String generateId() {
        return "EN" + String.format("%06d", counter++);
    }

    /**
     * Retrieves an enquiry by its ID.
     *
     * @param enquiryId The ID of the enquiry to retrieve
     * @return The Enquiry object associated with the given ID, or null if not found
     */
    public Enquiry getEnquiry(String enquiryId) {
        return enquiries.get(enquiryId);
    }

    /**
     * Retrieves all enquiries in the repository.
     *
     * @return A map of all enquiries, where the key is the enquiry ID and the value is the Enquiry object
     */
    public HashMap<String, Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * Retrieves a list of enquiry IDs for all enquiries authored by a specific person.
     *
     * @param authorId The ID of the author to filter enquiries by
     * @return A list of enquiry IDs associated with the given author
     */
    public ArrayList<String> getEnquiriesByAuthor(String authorId) {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Enquiry> entry : enquiries.entrySet()) {
            Enquiry enquiry = entry.getValue();
            if (enquiry.getAuthorId().equals(authorId)) {
                result.add(enquiry.getId());
            }
        }
        return result;
    }

    /**
     * Retrieves a list of enquiry IDs for all enquiries related to a specific project.
     *
     * @param projectName The name of the project to filter enquiries by
     * @return A list of enquiry IDs associated with the given project
     */
    public ArrayList<String> getEnquiriesByProject(String projectName) {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Enquiry> entry : enquiries.entrySet()) {
            Enquiry enquiry = entry.getValue();
            if (enquiry.getProjectName().equals(projectName)) {
                result.add(enquiry.getId());
            }
        }
        return result;
    }

    /**
     * Loads the enquiry data from the CSV file into the repository.
     * This method reads the file and populates the enquiries map.
     */
    public void loadFile() {
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();  // Skip header line
            String line;
            int maxId = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 7) continue;  // Skip invalid lines

                String enquiryId = parts[0];
                String projectId = parts[1];
                String authorId = parts[2];
                String title = parts[3];
                String message = parts[4];
                Enquiry.Status status = Enquiry.Status.PENDING;
                try {
                    status = Enquiry.Status.valueOf(parts[5]);
                } catch (IllegalArgumentException e) {
                    status = Enquiry.Status.PENDING;
                }
                String response = parts[6].equals("null") ? null : parts[6];

                Enquiry enquiry = new Enquiry(enquiryId, projectId, authorId, title, message);
                if (status == Enquiry.Status.ANSWERED) {
                    enquiry.respond(response);
                }
                enquiries.put(enquiryId, enquiry);

                try {
                    int idNum = Integer.parseInt(enquiryId.substring(2));  // Extract numeric part from the ID
                    if (idNum > maxId) maxId = idNum;
                } catch (NumberFormatException ignored) {}
            }

            counter = maxId + 1;  // Set the counter to the highest enquiry ID + 1
        } catch (IOException e) {
            e.printStackTrace();  // Log the error if file reading fails
        }
    }

    /**
     * Saves all current enquiries in the repository to the CSV file.
     * This method writes the data in CSV format.
     */
    public void saveFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("EnquiryID,Project Name,AuthorID,Title,Message,Status,Response");
            bw.newLine();

            for (Enquiry enquiry : enquiries.values()) {
                String response = enquiry.getResponse() == null ? "null" : enquiry.getResponse();
                String line = String.join(",",
                        enquiry.getId(),
                        enquiry.getProjectName(),
                        enquiry.getAuthorId(),
                        enquiry.getTitle(),
                        enquiry.getMessage(),
                        enquiry.getStatus().name(),
                        response);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();  // Log the error if file writing fails
        }
    }

    /**
     * Adds a new enquiry to the repository.
     *
     * @param enquiry The Enquiry object to be added
     */
    public void addEnquiry(Enquiry enquiry) {
        enquiries.put(enquiry.getId(), enquiry);
    }
}
