import java.io.*;
import java.time.format.*;
import java.util.*;
import java.time.*;


public class ProjectRepo {

    private static final String filePath = "data\\ProjectList.csv";
    private HashMap<String, Project> projectListings;

    public ProjectRepo() {
        this.projectListings = new HashMap<String, Project>();
        loadFile();
    }

    public int getNumOfProjects(){return projectListings.size();}

    public Project getProject(String name){
        return projectListings.get(name);
    }

    public void addProject(Project newProject) {
        this.projectListings.put(newProject.getName(), newProject);
    }

    private void loadFile() {
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Skip the header line
            br.readLine();

            String line = br.readLine();
            while (line != null) {
                String[] values = line.split(",");

                if (values.length >= 14) {
                    String name = values[0].trim();
                    String neighbourhood = values[1].trim();
                    Flat.Type type1 = Flat.Type.valueOf(values[2].trim().toUpperCase());
                    int numOfType1 = Integer.parseInt(values[3].trim());
                    int priceOfType1 = Integer.parseInt(values[4].trim());
                    Flat.Type type2 = Flat.Type.valueOf(values[5].trim().toUpperCase());
                    int numOfType2 = Integer.parseInt(values[6].trim());
                    int priceOfType2 = Integer.parseInt(values[7].trim());
                    LocalDate openDate = stringToDate(values[8].trim());
                    LocalDate closeDate = stringToDate(values[9].trim());
                    String managerId = values[10].trim();
                    int officerSlots = Integer.parseInt(values[11].trim());
                    ArrayList<String> assignedOfficers = stringToList(values[12]);
                    boolean visibility = Boolean.parseBoolean(values[13].trim().toLowerCase());
                    ArrayList<String> pendingOfficers = stringToList(values[14]);
                    ArrayList<Flat> flatInfo = flatInfoGen(type1, numOfType1, priceOfType1, type2, numOfType2, priceOfType2);

                    Project newProject = new Project(name,  neighbourhood,  flatInfo, openDate,  closeDate, managerId, officerSlots, assignedOfficers, visibility, pendingOfficers);
                    this.projectListings.put(name,newProject);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveProjects() {
        File file = new File(filePath);
        try {
            // First truncate the file (clear all contents)
            new FileOutputStream(file).close();

            // Then rewrite the file including any new Projects and changes made
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                // Write the header line
                bw.write("Project Name,Neighborhood,Type 1,Number of units for Type 1,Selling price for Type 1,Type 2,Number of units for Type 2,Selling price for Type 2,Application opening date,Application closing date,ManagerID,Officer Team Slots,Officer Team,Visibility,Pending Officers");
                bw.newLine();

                // Write each Project's data
                for (Project project: projectListings.values()) {
                    String line = String.join(",",
                            project.getName(),
                            project.getNeighbourhood(),
                            project.getFlatInfo().get(0).getFlatType().toString(),
                            String.valueOf(project.getFlatInfo().get(0).getTotalUnits()),
                            String.valueOf(project.getFlatInfo().get(0).getSellingPrice()),
                            project.getFlatInfo().get(1).getFlatType().toString(),
                            String.valueOf(project.getFlatInfo().get(1).getTotalUnits()),
                            String.valueOf(project.getFlatInfo().get(1).getSellingPrice()),
                            project.getOpenDate().toString(),
                            project.getCloseDate().toString(),
                            project.getManagerId(),
                            String.valueOf(project.getOfficerSlots()),
                            listToString(project.getAssignedOfficers()),
                            String.valueOf(project.isVisible()),
                            listToString(project.getPendingOfficers())
                    );
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }



    //helper methods for loadfile and savefile
    private ArrayList<Flat> flatInfoGen(Flat.Type type1, int numOfType1,int priceOfType1, Flat.Type type2, int numOfType2, int priceOfType2) {
        ArrayList<Flat> flatInfo = new ArrayList<>();
        Flat flat1 = new Flat(type1,numOfType1,priceOfType1);
        Flat flat2 = new Flat(type1,numOfType2,priceOfType2);
        flatInfo.add(flat1);
        flatInfo.add(flat2);
        return flatInfo;
    }

    private String listToString(ArrayList<String> list) {
        return String.join(",", list);
    }

    private ArrayList<String> stringToList(String values) {
        String[] splitValues = values.split(",");
        return new ArrayList<>(Arrays.asList(splitValues));
    }

    public LocalDate stringToDate(String stringDate) {
        if (stringDate == null || stringDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Date string cannot be null or empty");
        }

        // Handle both single-digit and double-digit day/month
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("[d/M/yyyy][dd/MM/yyyy]")  // Accepts both formats
                .parseStrict()  // Still validates actual date validity
                .toFormatter();

        try {
            return LocalDate.parse(stringDate.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid date: '" + stringDate + "'. Acceptable formats: d/M/yyyy or dd/MM/yyyy",
                    e
            );
        }
    }

}