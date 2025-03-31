import java.util.*;
import java.io.*;

public class HdbManagerController extends FileController{
        private static final String filePath = "Resource\\ManagerList.csv";
        private HashMap<String, HdbManager> managers;

    public HdbManagerController() {
        this.managers = new HashMap<String, HdbManager>();
        FileController.loadFile(filePath);
    }

    public void saveManagers(){
        FileController.saveFile(filePath,managers);
    }
    
}

