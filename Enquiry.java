package Project;

import java.util.List;

class Enquiry {
    private String content;
    private Applicant applicant;
    private Project project;
    private List<String> replies;

    public Enquiry(String content, Applicant applicant, Project project, List<String> replies) {
        this.content = content;
        this.applicant = applicant;
        this.project = project;
        this.replies = replies;
    }

    public String getContent() {
        return content;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Project getProject() {
        return project;
    }

    public List<String> getReplies() {
        return replies;
    }
}