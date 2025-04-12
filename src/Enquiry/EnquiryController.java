package Enquiry;
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

    private static boolean check(Enquiry enquiry) {
        if(enquiry == null) {
            System.out.println("No such enquiry exists!!");
            return false;
        }
        return true;
    }

    public static void deleteEnquiry(String enquiryId) {
        Enquiry enquiry = enquiries.get(enquiryId);
        if(!check(enquiry)) return;
        enquiries.remove(enquiryId);
        System.out.println("Enquiry " + enquiryId + " deleted successfully.");
    }

    // getter
    public static Enquiry getEnquiry(String enquiryId) {
        Enquiry enquiry = enquiries.get(enquiryId);
        if(!check(enquiry)) return null;
        return enquiry;
    }

    public static ArrayList<String> getEnquiriesByAuthor(String authorId) {
        ArrayList<String> ret = new ArrayList<>();
        for (Map.Entry<String, Enquiry> entry : enquiries.entrySet()) {
            Enquiry enquiry = entry.getValue();
            if(enquiry.getAuthorId().equals(authorId)) {
                ret.add(enquiry.getId());
            }
        }
        return ret;
    }

    public static ArrayList<String> getEnquiriesByProject(String projectId) {
        ArrayList<String> ret = new ArrayList<>();
        for (Map.Entry<String, Enquiry> entry : enquiries.entrySet()) {
            Enquiry enquiry = entry.getValue();
            if(enquiry.getProjectId().equals(projectId)) {
                ret.add(enquiry.getId());
            }
        }
        return ret;
    }

    // setter
    public static void setProjectId(String enquiryId, String projectId) {
        Enquiry enquiry = enquiries.get(enquiryId);
        if(!check(enquiry)) return;
        enquiry.setProjectId(projectId);
    }
    
    public static void setAuthorId(String enquiryId, String authorId) {
        Enquiry enquiry = enquiries.get(enquiryId);
        if(!check(enquiry)) return;
        enquiry.setAuthorId(authorId);
    }
    
    public static void setTitle(String enquiryId, String title) {
        Enquiry enquiry = enquiries.get(enquiryId);
        if(!check(enquiry)) return;
        enquiry.setTitle(title);
    }

    public static void setMessage(String enquiryId, String message) {
        Enquiry enquiry = enquiries.get(enquiryId);
        if(!check(enquiry)) return;
        enquiry.setMessage(message);
    }

    // main
    public static void replyEnquiry(String enquiryId, String response) {
        Enquiry enquiry = enquiries.get(enquiryId);
        if(!check(enquiry)) return;
        enquiry.respond(response);
    }
}
