package Report;

import Project.Flat;
import Users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Repository class for managing Report entities.
 * 
 * Responsible for loading, storing, filtering, and persisting Report data.
 * Acts as the "Entity" in the Boundary–Entity–Controller (BEC) architecture.
 */
public class ReportRepo {
    private static final String filePath = "data\\ReportList.csv";
    private static int counter = 0;
    private HashMap<String, Report> reports;

    /**
     * Initializes the ReportRepo and loads data from the CSV file.
     */
    public ReportRepo() {
        reports = new HashMap<>();
        loadFile();
    }

    /**
     * Generates a new unique report ID.
     * Format: RE followed by 6-digit zero-padded number.
     * @return generated report ID
     */
    public String generateId() {
        return "RE" + String.format("%06d", ++counter);
    }

    /**
     * Loads report data from the CSV file.
     * Populates the internal HashMap with existing report records.
     */
    public void loadFile() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            // Skip header
            if ((line = br.readLine()) == null) return;

            // Read data rows
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                String id = parts[0];
                String projectName = parts[1];
                String applicantId = parts[2];
                Flat.Type flatType = Flat.Type.valueOf(parts[3]);
                int age = Integer.parseInt(parts[4]);
                User.MaritalStatus status = User.MaritalStatus.valueOf(parts[5]);

                Report report = new Report(id, projectName, applicantId, flatType, age, status);
                reports.put(id, report);

                int numericPart = Integer.parseInt(id.substring(2));
                if (numericPart >= counter) counter = numericPart;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all report data to the CSV file.
     * Overwrites existing file contents.
     */
    public void saveFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            bw.write("ReportId,ProjectName,ApplicantId,FlatType,ApplicantAge,MaritalStatus");
            bw.newLine();

            // Write data rows
            for (Report report : reports.values()) {
                String line = String.join(",",
                        report.getId(),
                        report.getProjectName(),
                        report.getApplicantId(),
                        report.getFlatType().name(),
                        String.valueOf(report.getApplicantAge()),
                        report.getMartialStatus().name()
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new Report to the repository and returns its generated ID.
     * 
     * @param projectName the name of the project
     * @param applicantId the applicant's ID
     * @param flatType the type of flat
     * @param applicantAge the age of the applicant
     * @param martialStatus the marital status of the applicant
     * @return generated report ID
     */
    public String addReport(String projectName, String applicantId, Flat.Type flatType,
                            int applicantAge, User.MaritalStatus martialStatus) {
        String reportId = generateId();
        reports.put(reportId, new Report(reportId, projectName, applicantId, flatType, applicantAge, martialStatus));
        return reportId;
    }

    /**
     * Retrieves a report by its ID.
     * @param reportId the ID of the report
     * @return Report object or null if not found
     */
    public Report getReport(String reportId) {
        return reports.get(reportId);
    }

    /**
     * Deletes a report by its ID.
     * @param reportId the ID of the report to delete
     */
    public void deleteReport(String reportId) {
        reports.remove(reportId);
    }

    /**
     * Retrieves all reports for a given project.
     * @param projectName the name of the project
     * @return list of matching reports
     */
    public ArrayList<Report> getReportsByProject(String projectName) {
        ArrayList<Report> ret = new ArrayList<>();
        for(Report e : reports.values()) {
            if(e.getProjectName().compareTo(projectName) == 0) ret.add(e);
        }
        return ret;
    }

    /**
     * Retrieves all reports for a given applicant.
     * @param applicantId the applicant's ID
     * @return list of matching reports
     */
    public ArrayList<Report> getReportsByApplicant(String applicantId) {
        ArrayList<Report> ret = new ArrayList<>();
        for(Report e : reports.values()) {
            if(e.getApplicantId().compareTo(applicantId) == 0) ret.add(e);
        }
        return ret;
    }

    /**
     * Retrieves all reports that match the given marital status.
     * @param maritalStatus the marital status to filter by
     * @return list of matching reports
     */
    public ArrayList<Report> getReportsByMaritalStatus(User.MaritalStatus maritalStatus) {
        ArrayList<Report> ret = new ArrayList<>();
        for(Report e : reports.values()) {
            if(e.getMartialStatus() == maritalStatus) ret.add(e);
        }
        return ret;
    }

    /**
     * Retrieves all reports that match the given flat type.
     * @param flatType the flat type to filter by
     * @return list of matching reports
     */
    public ArrayList<Report> getReportsByFlatType(Flat.Type flatType) {
        ArrayList<Report> ret = new ArrayList<>();
        for(Report e : reports.values()) {
            if(e.getFlatType() == flatType) ret.add(e);
        }
        return ret;
    }

    /**
     * Gets the entire report collection.
     * @return map of all reports by their ID
     */
    public HashMap<String, Report> getReports() {
        return reports;
    }
}
