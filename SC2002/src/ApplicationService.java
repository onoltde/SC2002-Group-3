public class ApplicationService {

    public Application createApplication (Project project, User user ){     //need to include logic that auto rejects managers/officers/applicants

        if (user instanceof Manager){
            user.checkValid()
        }

        Application newApplication = new Application(projectId, applicationId);

        return newApplication;
    }

    public Boolean withdraw(String applicationId){

    }


}
