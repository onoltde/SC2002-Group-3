public interface UserUI<U extends User>{

     U displayLogin();
     U login();
    void forgetPassword();
    void displayDashboard(U user);

    default void exitMessage(){
        printDivider();
        System.out.println("Thank you for using BTO portal. Goodbye!");
    }

    default void printDivider(){
        System.out.println("=========================================================================");
    }
}
