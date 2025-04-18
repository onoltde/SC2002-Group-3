package Application;
import HdbOfficer.*;
import HdbManager.*;

public interface TeamApplicationControllerInterface{

    void displayApplicationMenu(HdbManager manager);
    void displayApplicationMenu(HdbOfficer officer);
    ApplicationRepo getRepo();

}
