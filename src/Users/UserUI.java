package Users;

import Utility.InputUtils;

/**
 * Interface defining the user interface operations for any user type.
 * Includes login, password handling, and dashboard display.
 *
 * @param <U> a subtype of User
 * @param <R> a subtype of UserRepo for managing user data
 */
public interface UserUI<U extends User, R extends UserRepo> {

    /**
     * Displays the login interface and validates the user's credentials.
     *
     * @param userRepo the repository to verify user data
     * @return the authenticated user, or null if login fails
     */
    U displayLogin(R userRepo);

    /**
     * Performs the login process for the user.
     *
     * @param userRepo the repository containing user data
     * @return the logged-in user, or null if credentials are invalid
     */
    U login(R userRepo);

    /**
     * Handles the forgotten password flow and resets it if the user is verified.
     *
     * @param userRepo the repository with user records
     */
    void forgetPassword(R userRepo);

    /**
     * Displays the dashboard interface specific to the user.
     *
     * @param user the user whose dashboard is to be displayed
     */
    void displayDashboard(U user);

    /**
     * Allows the user to change their password.
     *
     * @param user the user changing their password
     */
    void changePassword(U user);

    /**
     * Displays a standard message when exiting back to the main menu.
     */
    default void exitToMenu(){
        System.out.println("Returning to Menu...");
        InputUtils.printBigDivider();
    }
}
