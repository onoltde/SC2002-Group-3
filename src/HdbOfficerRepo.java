import java.util.*;
import java.io.*;

public class HdbOfficerRepo implements UserRepo <HdbOfficer> {
    private static final String filePath = "data\\OfficerList.csv";
    private static HashMap<String, HdbOfficer> officers;

    public HdbOfficerRepo() {
        officers = new HashMap<String, HdbOfficer>();
        loadFile();
    }

    public void loadFile() {
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Skip the header line
            br.readLine();

            String line = br.readLine();
            while (line != null) {
                String[] values = line.split(",");

                if (values.length >= 5) {
                    String name = values[0].trim();
                    String nric = values[1].trim();
                    int age = Integer.parseInt(values[2].trim());
                    String password = values[4].trim();
                    User.MaritalStatus maritalStatus = User.MaritalStatus.valueOf(values[3].trim().toUpperCase());
                    HdbOfficer newOfficer  = new HdbOfficer(name, nric, age, maritalStatus, password);
                    addUser(newOfficer);

                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveFile(){
        File file = new File(filePath);
        try {
            // First truncate the file (clear all contents)
            new FileOutputStream(file).close();

            // Then rewrite the file including any new officers and changes made
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                // Write the header line
                bw.write("Name,NRIC,Age,Marital Status,Password,OfficerId");
                bw.newLine();

                // Write each applicant's data
                for (HdbOfficer officerPerson : officers.values()) {
                    String line = String.join(",",
                            officerPerson.getName(),
                            officerPerson.getNric(),
                            String.valueOf(officerPerson.getAge()),
                            officerPerson.getMaritalStatus().toString(),
                            officerPerson.getPassword(),
                            officerPerson.getId()
                    );
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void addUser(HdbOfficer newOfficer){
        officers.put(newOfficer.getId(), newOfficer);
    }

    public HdbOfficer getUser(String officerId){
        return officers.get(officerId) ;
    }

    public  String generateID(String nric){
        return "OF-" + nric.substring(5);
    }

    //for debugging
    public void printOfficers(){
        for (HdbOfficer officer : officers.values()){
            System.out.println(officer.toString());
        }
    }

}





