public interface UserUI<U extends User>{

     U displayLogin();
     U login();
    void forgetPassword();
    void displayDashboard(U user);

    default void exitToMenu(){
        System.out.println("Returning to Menu...");
        printDivider();
    }

    default void printDivider(){
        System.out.println("===================================================================");
    }
}
