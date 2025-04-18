package Application;
import HdbOfficer.*;
import HdbManager.*;
import Applicant.*;

import java.util.ArrayList;

public interface ResidentialApplicationControllerInterface{

    void displayApplicationMenu(Applicant applicant);
    ArrayList<String> displayApplicationMenu(HdbManager manager);
    void displayApplicationMenu(HdbOfficer officer);
    ApplicationRepo getRepo();

}
