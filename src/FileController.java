//import java.io.*;
//import java.util.*;
//
//public class FileController {
//
//    private HashMap<String, User> users;
//
//    public FileController() {
//
//    }
//
//    public static HashMap<String, User> loadFile(String filePath) {
//
//        HashMap<String, User> users = new HashMap<String, User>();
//
//        File file = new File(filePath);
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            // Skip the header line
//            br.readLine();
//
//            String line = br.readLine();
//            while (line != null) {
//                String[] values = line.split(",");
//
//                if (values.length >= 5) {
//                    String name = values[0].trim();
//                    String nric = values[1].trim();
//                    int age = Integer.parseInt(values[2].trim());
//                    String password = values[4].trim();
//                    User.MaritalStatus maritalStatus = User.MaritalStatus.valueOf(values[3].trim().toUpperCase());
//
//                    User newUser = new User(name, nric, age, maritalStatus, password);
//                    users.put(newUser.getId(), newUser);
//                }
//                line = br.readLine();
//            }
//        } catch (IOException e) {
//            System.err.println("Error reading file: " + e.getMessage());
//        }
//        return users;
//    }
//
//    public static void saveFile(String filePath, HashMap<String, User> users) {
//        File file = new File(filePath);
//
//        // First truncate the file (clear all contents)
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            // Empty the file (truncate)
//        } catch (IOException e) {
//            System.err.println("Error clearing file: " + e.getMessage());
//            return; // Exit if we can't even clear the file
//        }
//
//        // Then rewrite the file with updated user data
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
//            // Write the header line
//            bw.write("Name,NRIC,Age,Marital Status,Password,UserSpecificId");
//            bw.newLine();
//
//            // Write each user's data
//            for (User user : users.values()) {
//                String line = String.join(",",
//                        user.getName(),
//                        user.getNric(),
//                        String.valueOf(user.getAge()),
//                        user.getMaritalStatus().toString(),
//                        user.getPassword(),
//                        user.getId()
//                );
//                bw.write(line);
//                bw.newLine();
//            }
//        } catch (IOException e) {
//            System.err.println("Error writing to file: " + e.getMessage());
//        }
//    }
//
//}