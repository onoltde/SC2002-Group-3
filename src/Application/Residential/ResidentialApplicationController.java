package Application.Residential;

import Application.Application;
import Application.ApplicationController;
import HdbManager.HdbManager;
import HdbOfficer.HdbOfficer;
import Project.Flat;
import Project.Project;
import Project.ProjectController;
import Users.UserController;
import Applicant.*;
import Utility.InputUtils;


public class ResidentialApplicationController implements ApplicationController{

    private UserController sourceController;
    private ResidentialApplicationUI resAppUI;

    public ResidentialApplicationController(UserController sourceController) {
        this.sourceController = sourceController;
        resAppUI = new ResidentialApplicationUI(this);
    }

    public void displayApplicationMenu(Applicant applicant) {
        resAppUI.displayApplicationMenu(applicant);
    }

    @Override   //to apply as resident
    public void displayApplicationMenu(HdbOfficer officer) {
        resAppUI.displayApplicationMenu(officer);
    }

    @Override
    public void displayApplicationMenu(HdbManager manager) {

    }

    public void requestWithdrawal(ResidentialApplication application){
        if (resAppUI.requestWithdrawal(application)){
            if (application.getStatus() == Application.Status.BOOKED){
                //NEED TO IMPLEMENT WAY TO UPDATE BOOKED NUMBER!!!
            }
            application.updateStatus(Application.Status.WITHDRAWING);
        }
    }

    public void displayProjectsToApply(HdbOfficer officer, Flat.Type flatType) {
        ProjectController projectController = new ProjectController(this);
        projectController.displayResProjectsToApply(officer,flatType);
    }
}//end of class