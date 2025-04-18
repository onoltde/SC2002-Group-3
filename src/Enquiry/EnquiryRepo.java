package Enquiry;
import java.util.*;
import java.io.*;

public class EnquiryRepo {
    private static final String filePath = "data\\EnquiryList.csv";
    private static int counter = 0;
    private HashMap<String, Enquiry> enquiries;

    public EnquiryRepo() {
        enquiries = new HashMap<>();
        loadFile();
    }

    public String generateId() {
        return "EN" + String.format("%06d", counter++);
    }

    public Enquiry getEnquiry(String enquiryId) {
        return enquiries.get(enquiryId);
    }

    public HashMap<String, Enquiry> getEnquiries() {
        return enquiries;
    }

    public ArrayList<String> getEnquiriesByAuthor(String authorId) {
        ArrayList<String> ret = new ArrayList<>();
        for (Map.Entry<String, Enquiry> entry : enquiries.entrySet()) {
            Enquiry enquiry = entry.getValue();
            if(enquiry.getAuthorId().equals(authorId)) {
                ret.add(enquiry.getId());
            }
        }
        return ret;
    }

    public ArrayList<String> getEnquiriesByProject(String name) {
        ArrayList<String> ret = new ArrayList<>();
        for (Map.Entry<String, Enquiry> entry : enquiries.entrySet()) {
            Enquiry enquiry = entry.getValue();
            if(enquiry.getProjectName().equals(name)) {
                ret.add(enquiry.getId());
            }
        }
        return ret;
    }

    public void loadFile() {
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            int maxId = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 7) continue;

                String enquiryId = parts[0];
                String projectId = parts[1];
                String authorId = parts[2];
                String title = parts[3];
                String message = parts[4];
                EnquiryStatus status;
                try {
                    status = EnquiryStatus.valueOf(parts[5]);
                } catch (IllegalArgumentException e) {
                    status = EnquiryStatus.PENDING;
                }
                String response = parts[6].equals("null") ? null : parts[6];

                Enquiry enq = new Enquiry(enquiryId, projectId, authorId, title, message);
                if (status == EnquiryStatus.ANSWERED) {
                    enq.respond(response);
                }
                enquiries.put(enquiryId, enq);

                try {
                    int idNum = Integer.parseInt(enquiryId.substring(2));
                    if (idNum > maxId) maxId = idNum;
                } catch (NumberFormatException ignored) {}
            }

            counter = maxId + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("EnquiryID,Project Name,AuthorID,Title,Message,Status,Response");
            bw.newLine();

            for (Enquiry enq : enquiries.values()) {
                String response = enq.getResponse() == null ? "null" : enq.getResponse();
                String line = String.join(",",
                        enq.getId(),
                        enq.getProjectName(),
                        enq.getAuthorId(),
                        enq.getTitle(),
                        enq.getMessage(),
                        enq.getStatus().name(),
                        response);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.put(enquiry.getId(), enquiry);
    }
}
