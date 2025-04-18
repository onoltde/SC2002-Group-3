package Application;
import HdbOfficer.*;
import HdbManager.*;
import Applicant.*;

public interface ResidentialApplicationControllerInterface{

    void displayApplicationMenu(Applicant applicant);
    void displayApplicationMenu(HdbManager manager);
    void displayApplicationMenu(HdbOfficer officer);
    ApplicationRepo getRepo();

}
