import java.util.*;


public class Main {
    public static void main(String[] args) {


//        new ApplicantRepo();
//        ApplicantRepo.printApplicants();
//        ApplicantRepo.saveApplicants();
//        System.out.println("\n");
//        HdbManagerRepo repo2 = new HdbManagerRepo();
//        repo2.printManagers();
//        System.out.println("\n");
//        HdbOfficerRepo repo3 = new HdbOfficerRepo();
//        repo3.printOfficers();

        //ACTUAL MAIN METHOD DONT DELETE
        Scanner sc = new Scanner(System.in);
        new MainMenu(sc);
        MainMenu.displayWelcome();
        sc.close();

    }
}//end of class