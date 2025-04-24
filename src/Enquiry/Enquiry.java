package Enquiry;

import java.util.*;
import java.io.*;

/**
 * Represents an enquiry made by a user related to a specific project.
 * Each enquiry has a title, message, status, and an optional response.
 */
public class Enquiry {
	private String enquiryId;
	private String projectName;
	private String authorId;
	private String title;
	private String message;
	private Enquiry.Status status;
	private String response;

	/**
	 * Enum representing the possible status of an enquiry.
	 */
	public enum Status {
		PENDING,    // Enquiry is pending a response
		ANSWERED    // Enquiry has been answered
	}

	/**
	 * Constructs an Enquiry object with the provided details.
	 * Initially, the status is set to PENDING and the response is null.
	 *
	 * @param enquiryId  Unique identifier for the enquiry
	 * @param projectName Name of the project related to the enquiry
	 * @param authorId    ID of the person who made the enquiry
	 * @param title       Title of the enquiry
	 * @param message     The content of the enquiry
	 */
	public Enquiry(String enquiryId, String projectName, String authorId, String title, String message) {
		this.enquiryId = enquiryId;
		this.projectName = projectName;
		this.authorId = authorId;
		this.title = title;
		this.message = message;
		this.response = null;
		this.status = Enquiry.Status.PENDING;
	}

	// Getter methods

	/**
	 * Gets the unique identifier of the enquiry.
	 *
	 * @return enquiryId The ID of the enquiry
	 */
	public String getId() {
		return enquiryId;
	}

	/**
	 * Gets the name of the project associated with the enquiry.
	 *
	 * @return projectName The project name
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Gets the ID of the person who authored the enquiry.
	 *
	 * @return authorId The ID of the author
	 */
	public String getAuthorId() {
		return authorId;
	}

	/**
	 * Gets the title of the enquiry.
	 *
	 * @return title The title of the enquiry
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the content of the enquiry message.
	 *
	 * @return message The enquiry message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the response to the enquiry if provided.
	 *
	 * @return response The response message, or null if unanswered
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Gets the current status of the enquiry (PENDING or ANSWERED).
	 *
	 * @return status The status of the enquiry
	 */
	public Enquiry.Status getStatus() {
		return status;
	}

	// Setter methods

	/**
	 * Sets the project name associated with the enquiry.
	 *
	 * @param projectName The new project name
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Sets the author ID associated with the enquiry.
	 *
	 * @param authorId The new author ID
	 */
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	/**
	 * Sets the title of the enquiry.
	 *
	 * @param title The new title of the enquiry
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the message content of the enquiry.
	 *
	 * @param message The new message content
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Responds to the enquiry, changing its status to ANSWERED and setting the response.
	 *
	 * @param response The response to the enquiry
	 */
	public void respond(String response) {
		status = Enquiry.Status.ANSWERED;
		this.response = response;
	}
}
