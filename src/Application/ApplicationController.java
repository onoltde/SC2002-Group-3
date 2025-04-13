package Application;
import Applicant.*;
import HdbOfficer.*;
import HdbManager.*;

public interface ApplicationController{

    public void displayApplicationMenu(HdbManager manager);
    public void displayApplicationMenu(HdbOfficer officer);
}
