package Enquiry;
import java.util.*;
import java.io.*;

public class EnquiryController {
    private EnquiryRepo repo;

    public EnquiryController() {
        repo = new EnquiryRepo();
    }

    public void addEnquiry(String projectId, String authorId, String title, String message) {
        String enquiryId = repo.generateId();
        repo.addEnqruiry(new Enquiry(enquiryId, projectId, authorId, title, message));
    }

    // getter
    public Enquiry getEnquiry(String enquiryId) {
        return repo.getEnquiry(enquiryId);
    }

    public EnquiryRepo getRepo() {
        return repo;
    }
}
