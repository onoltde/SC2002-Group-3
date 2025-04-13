package Enquiry;
import java.util.*;
import java.io.*;

public class Enquiry {
	private String enquiryId;
	private String projectId;
	private String authorId;
	private String title;
	private String message;
	private EnquiryStatus status;
	private String response;

	public Enquiry(String enquiryId, String projectId, String authorId, String title, String message) {
		this.enquiryId = enquiryId;
		this.projectId = projectId;
		this.authorId = authorId;
		this.title = title;
		this.message = message;
		this.response = null;
		this.status = EnquiryStatus.PENDING;
	}

	// getter
	public String getId() { return enquiryId; }
	public String getProjectId() { return projectId; }
	public String getAuthorId() { return authorId; }
	public String getTitle() { return title; }
	public String getMessage() { return message; }
	public String getResponse() { return response; }
	public EnquiryStatus getStatus() { return status; }

	// setter
	public void setProjectId(String projectId) { this.projectId = projectId; }
	public void setAuthorId(String authorId) { this.authorId = authorId; }
	public void setTitle(String title) { this.title = title; }
	public void setMessage(String message) { this.message = message; }

	// viewer
	// !!

	public void respond(String response) {
		status = EnquiryStatus.ANSWERED;
		this.response = response;
	}
}