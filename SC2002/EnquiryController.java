import java.util.*;
import java.io.*;

public class EnquiryController {
    private static HashMap<String, Enquiry> enquiries = new HashMap<>();
    private static int counter = 0;

    public static void addEnquiry(String projectId, String authorId, String title, String message) {
        counter++;
        String enquiryId = "EN" + String.format("%06d", counter);
        enquiries.put(enquiryId, new Enquiry(enquiryId, projectId, authorId, title, message));
    }

    public static void deleteEnquiry(String enquiryId) {
        if (!enquiries.containsKey(enquiryId)) {
            System.out.println("No such enquiry exists!!");
            return;
        }
        enquiries.remove(enquiryId);
        System.out.println("Enquiry " + enquiryId + " deleted successfully.");
    }

    // getter
    public static Enquiry getEnquiry(String enquiryId) {
        return enquiries.get(enquiryId);
    }

    public static ArrayList<String> getEnquiriesByAuthor(String authorId) {
        ArrayList<String> ret = new ArrayList<>();
        for (Map.Entry<String, Enquiry> entry : enquiries.entrySet()) {
            Enquiry enquiry = entry.getValue();
            if(enquiry.getAuthorId() == authorId) {
                ret.add(enquiry.getId());
            }
        }
        return ret;
    }

    public static ArrayList<String> getEnquiriesByProject(String projectId) {
        ArrayList<String> ret = new ArrayList<>();
        for (Map.Entry<String, Enquiry> entry : enquiries.entrySet()) {
            Enquiry enquiry = entry.getValue();
            if(enquiry.getProjectId() == projectId) {
                ret.add(enquiry.getId());
            }
        }
        return ret;
    }

    // setter
    public static void setProjectId(String enquiryId, String projectId) {
        if(!enquiries.containsKey(enquiryId)) {
            System.out.println("No such enquiry exists!!");
            return;
        }
        enquiries.get(enquiryId).setProjectId(projectId);
    }
    
    public static void setAuthorId(String enquiryId, String authorId) {
        if(!enquiries.containsKey(enquiryId)) {
            System.out.println("No such enquiry exists!!");
            return;
        }
        enquiries.get(enquiryId).setAuthorId(authorId);
    }
    
    public static void setTitle(String enquiryId, String title) {
        if(!enquiries.containsKey(enquiryId)) {
            System.out.println("No such enquiry exists!!");
            return;
        }
        enquiries.get(enquiryId).setTitle(title);
    }

    public static void setMessage(String enquiryId, String message) {
        if(!enquiries.containsKey(enquiryId)) {
            System.out.println("No such enquiry exists!!");
            return;
        }
        enquiries.get(enquiryId).setMessage(message);
    }

    // main
    public static void replyEnquiry(String enquiryId, String response) {
        if(!enquiries.containsKey(enquiryId)) {
            System.out.println("No such enquiry!!");
            return;
        }
        enquiries.get(enquiryId).respond(response);
    }
}
