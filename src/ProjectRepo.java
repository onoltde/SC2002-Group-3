import java.io.*;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;


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

            String line;
            while ((line = br.readLine()) != null) {
                // Split on commas NOT inside quotes, then trim quotes
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Clean each value by removing surrounding quotes
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("^\"|\"$", "").trim();
                }

                if (values.length >= 2) {
                    String name = values[0].trim();
                    String neighbourhood = values[1].trim();
                    Flat.Type type1 = Flat.Type.valueOf(values[2].trim().toUpperCase());
                    int totalNumOfType1 = Integer.parseInt(values[3].trim());
                    int numOfType1Booked = Integer.parseInt(values[4].trim());
                    int priceOfType1 = Integer.parseInt(values[5].trim());
                    Flat.Type type2 = Flat.Type.valueOf(values[6].trim().toUpperCase());
                    int totalNumOfType2 = Integer.parseInt(values[7].trim());
                    int numOfType2Booked = Integer.parseInt(values[8].trim());
                    int priceOfType2 = Integer.parseInt(values[9].trim());
                    LocalDate openDate = TimeUtils.stringToDate(values[10].trim());
                    LocalDate closeDate = TimeUtils.stringToDate(values[11].trim());
                    String managerId = values[12].trim();
                    int officerSlots = Integer.parseInt(values[13].trim());
                    ArrayList<String> assignedOfficers = stringToList(values[14]);
                    boolean visibility = Boolean.parseBoolean(values[15].trim().toLowerCase());
                    ArrayList<String> pendingOfficers = stringToList(values[16]);
                    HashMap<Flat.Type,Flat> flatInfo = flatInfoGen(type1, totalNumOfType1, numOfType1Booked, priceOfType1, type2, totalNumOfType2,numOfType2Booked, priceOfType2);

                    Project newProject = new Project(name,  neighbourhood,  flatInfo, openDate,  closeDate, managerId, officerSlots, assignedOfficers, visibility, pendingOfficers);
                    this.projectListings.put(name,newProject);
                }

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
                bw.write("Project Name,Neighborhood,Type 1,Total number of units for Type 1,Number of units Booked for Type 1,Selling price for Type 1,Type 2,Total number of units for Type 2,Number of units Booked for Type 2,Selling price for Type 2,Application opening date,Application closing date,ManagerID,Officer Team Slots,Officer Team,Visibility,Pending Officers,END");
                bw.newLine();

                // Write each Project's data
                for (Project project : projectListings.values()) {
                    StringBuilder sb = new StringBuilder();

                    // Get flats for easier reference
                    Flat type1Flat = project.getFlatInfo().get(Flat.Type.TWOROOM);
                    Flat type2Flat = project.getFlatInfo().get(Flat.Type.THREEROOM);

                    // Build the CSV line
                    sb.append(project.getName()).append(",")
                            .append(project.getNeighbourhood()).append(",")
                            .append(type1Flat.getFlatType()).append(",")
                            .append(type1Flat.getTotalUnits()).append(",")
                            .append(type1Flat.getUnitsBooked()).append(",")
                            .append(type1Flat.getSellingPrice()).append(",")
                            .append(type2Flat.getFlatType()).append(",")
                            .append(type2Flat.getTotalUnits()).append(",")
                            .append(type2Flat.getUnitsBooked()).append(",")
                            .append(type2Flat.getSellingPrice()).append(",")
                            .append(TimeUtils.dateToString(project.getOpenDate())).append(",")
                            .append(TimeUtils.dateToString(project.getCloseDate())).append(",")
                            .append(project.getManagerId()).append(",")
                            .append(project.getOfficerSlots()).append(",")
                            .append(listToString(project.getAssignedOfficers())).append(",")
                            .append(project.isVisible()).append(",")
                            .append(listToString(project.getPendingOfficers())).append(",")
                            .append("END");

                    bw.write(sb.toString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    public HashMap<String,Project> getProjectListings(){
        return projectListings;
    }

    //filter methods
    public ArrayList<Project> filterByAvailUnitType(Flat.Type roomType) {
        return projectListings.values().stream()
                .filter(project -> project.isVisible()
                        && project.isWithinDateRange()
                        && project.hasAvailUnits(roomType))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //helper methods for loadfile and savefile
    private HashMap<Flat.Type,Flat> flatInfoGen(Flat.Type type1, int totalNumOfType1, int numOfType1Booked,int priceOfType1, Flat.Type type2, int totalNumOfType2, int numOfType2Booked, int priceOfType2) {
        HashMap<Flat.Type,Flat> flatInfo = new HashMap<>();
        Flat flat1 = new Flat(type1,totalNumOfType1,numOfType1Booked,priceOfType1);
        Flat flat2 = new Flat(type2,totalNumOfType2,numOfType2Booked,priceOfType2);
        flatInfo.put(type1,flat1);
        flatInfo.put(type2,flat2);
        return flatInfo;
    }

    public static String listToString(ArrayList<String> list) {
        // Join elements with ", " delimiter
        String joined = String.join(", ", list);
        // Add surrounding quotes
        return "\"" + joined + "\"";
    }

    public static ArrayList<String> stringToList(String input) {
        ArrayList<String> result = new ArrayList<>();

        // Handle null or empty input
        if (input == null || input.trim().isEmpty()) {
            return result;  // Return empty list
        }

        // Remove surrounding quotes if they exist
        String content = input.trim();
        if (content.startsWith("\"") && content.endsWith("\"")) {
            content = content.substring(1, content.length() - 1);
        }

        // Handle case where content is empty after quote removal
        if (content.isEmpty()) {
            return result;
        }

        // Split by comma and trim whitespace from each element
        // Also filter out any empty strings that might result from splitting
        result = new ArrayList<>(Arrays.asList(content.split("\\s*,\\s*")))
                .stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));

        return result;
    }




}//end of class