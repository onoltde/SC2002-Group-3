/**
 * Entry point for the BTO Management System.
 * 
 * This class contains the main method which initializes the main menu and
 * starts the application. It should not be modified unless necessary to 
 * change the application startup behavior.
 * 
 */
public class Main {
    /**
     * The main method which launches the BTO Management System.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // ACTUAL MAIN METHOD DON'T DELETE
        new MainMenu();
        MainMenu.displayWelcome();
    }
}
