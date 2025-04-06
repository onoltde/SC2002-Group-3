//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class FlatBookingService {
//
//    //applicant overloaded method
//    public boolean bookFlat(Applicant applicant) {
//
//        //check if applicant has application
//
//
//    }
//
//    private void generateReceipt(Applicant applicant, Project project) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        System.out.println("\n===== Flat Booking Receipt =====");
//        System.out.println("Name: " + applicant.getName());
//        System.out.println("NRIC: " + applicant.getNric());
//        System.out.println("Age: " + applicant.getAge());
//        System.out.println("Marital Status: " + applicant.getMaritalStatus());
//        System.out.println("Flat Type: " + applicant.getBookedFlat().getFlatType());
//        System.out.println("Project: " + project.getName());
//        System.out.println("Booked On: " + applicant.getBookedFlat().getBookedOn().format(formatter));
//        System.out.println("================================\n");
//    }
//}
