package Report;

import Project.Flat;
import Users.User;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportRepo {
    private static final String filePath = "data\\ReportList.csv";
    private static int counter = 0;
    private HashMap<String, Report> reports;

    public ReportRepo() {
        reports = new HashMap<>();
        loadFile();
    }

    public String generateId() {
        return "RE" + String.format("%06d", ++counter);
    }

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

    public String addReport(String projectName, String applicantId, Flat.Type flatType,
                            int applicantAge, User.MaritalStatus martialStatus) {
        String reportId = generateId();
        reports.put(reportId, new Report(reportId, projectName, applicantId, flatType, applicantAge, martialStatus));
        return reportId;
    }

    public Report getReport(String reportId) {
        return reports.get(reportId);
    }

    public void deleteReport(String reportId) {
        reports.remove(reportId);
    }
    // filter
    public ArrayList<Report> getReportsByProject(String projectName) {
        ArrayList<Report> ret = new ArrayList<>();
        for(Report e : reports.values()) {
            if(e.getProjectName().compareTo(projectName) == 0) ret.add(e);
        }
        return ret;
    }
    public ArrayList<Report> getReportsByApplicant(String applicantId) {
        ArrayList<Report> ret = new ArrayList<>();
        for(Report e : reports.values()) {
            if(e.getApplicantId().compareTo(applicantId) == 0) ret.add(e);
        }
        return ret;
    }
    public ArrayList<Report> getReportsByMaritalStatus(User.MaritalStatus maritalStatus) {
        ArrayList<Report> ret = new ArrayList<>();
        for(Report e : reports.values()) {
            if(e.getMartialStatus() == maritalStatus) ret.add(e);
        }
        return ret;
    }
    public ArrayList<Report> getReportsByFlatType(Flat.Type flatType) {
        ArrayList<Report> ret = new ArrayList<>();
        for(Report e : reports.values()) {
            if(e.getFlatType() == flatType) ret.add(e);
        }
        return ret;
    }

    public HashMap<String, Report> getReports() {
        return reports;
    }
}
