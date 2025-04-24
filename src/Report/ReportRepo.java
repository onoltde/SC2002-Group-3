package Report;

import Project.Flat;
import Users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles the storage, retrieval, and filtering of reports.
 * Reads from and writes to a CSV file.
 */
public class ReportRepo {
    private static final String filePath = "data\\ReportList.csv";
    private static int counter = 0;
    private HashMap<String, Report> reports;

    /**
     * Constructs a ReportRepo and loads data from file.
     */
    public ReportRepo() {
        reports = new HashMap<>();
        loadFile();
    }

    /**
     * Generates a new unique report ID.
     *
     * @return the generated report ID
     */
    public String generateId() {
        return "RE" + String.format("%06d", counter++);
    }

    /**
     * Loads report data from the CSV file into memory.
     */
    public void loadFile() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            // Skip header
            if ((line = br.readLine()) == null) return;

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
                if (numericPart + 1 >= counter) counter = numericPart + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all reports to the CSV file.
     */
    public void saveFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("ReportId,ProjectName,ApplicantId,FlatType,ApplicantAge,MaritalStatus");
            bw.newLine();

            for (Report report : reports.values()) {
                String line = String.join(",",
                        report.getId(),
                        report.getProjectName(),
                        report.getApplicantId(),
                        report.getFlatType().name(),
                        String.valueOf(report.getApplicantAge()),
                        report.getMaritalStatus().name()
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new report to the repository.
     *
     * @param projectName   the project name
     * @param applicantId   the applicant ID
     * @param flatType      the flat type
     * @param applicantAge  the applicant's age
     * @param maritalStatus the applicant's marital status
     * @return the generated report ID
     */
    public String addReport(String projectName, String applicantId, Flat.Type flatType,
                            int applicantAge, User.MaritalStatus maritalStatus) {
        String reportId = generateId();
        reports.put(reportId, new Report(reportId, projectName, applicantId, flatType, applicantAge, maritalStatus));
        return reportId;
    }

    /**
     * Retrieves a report by its ID.
     *
     * @param reportId the report ID
     * @return the report, or null if not found
     */
    public Report getReport(String reportId) {
        return reports.get(reportId);
    }

    /**
     * Deletes a report by its ID.
     *
     * @param reportId the report ID
     */
    public void deleteReport(String reportId) {
        reports.remove(reportId);
    }

    /**
     * Filters reports by project name.
     *
     * @param projectName the project name
     * @return list of reports matching the project name
     */
    public ArrayList<Report> getReportsByProject(String projectName) {
        ArrayList<Report> result = new ArrayList<>();
        for (Report report : reports.values()) {
            if (report.getProjectName().equals(projectName)) {
                result.add(report);
            }
        }
        return result;
    }

    /**
     * Filters reports by applicant ID.
     *
     * @param applicantId the applicant ID
     * @return list of reports for the given applicant
     */
    public ArrayList<Report> getReportsByApplicant(String applicantId) {
        ArrayList<Report> result = new ArrayList<>();
        for (Report report : reports.values()) {
            if (report.getApplicantId().equals(applicantId)) {
                result.add(report);
            }
        }
        return result;
    }

    /**
     * Filters reports by marital status.
     *
     * @param maritalStatus the marital status
     * @return list of reports matching the marital status
     */
    public ArrayList<Report> getReportsByMaritalStatus(User.MaritalStatus maritalStatus) {
        ArrayList<Report> result = new ArrayList<>();
        for (Report report : reports.values()) {
            if (report.getMaritalStatus() == maritalStatus) {
                result.add(report);
            }
        }
        return result;
    }

    /**
     * Filters reports by flat type.
     *
     * @param flatType the flat type
     * @return list of reports matching the flat type
     */
    public ArrayList<Report> getReportsByFlatType(Flat.Type flatType) {
        ArrayList<Report> result = new ArrayList<>();
        for (Report report : reports.values()) {
            if (report.getFlatType() == flatType) {
                result.add(report);
            }
        }
        return result;
    }

    /**
     * @return a map of all reports
     */
    public HashMap<String, Report> getReports() {
        return reports;
    }
}
