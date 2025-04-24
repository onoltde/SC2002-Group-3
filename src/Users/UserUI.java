package Users;

import Utility.InputUtils;

/**
 * Interface that defines methods for user interface operations related to user login,
 * password management, and dashboard interaction.
 *
 * @param <U> The type of user this interface manages, extending the {@link User} class.
 * @param <R> The type of user repository this interface interacts with, extending the {@link UserRepo} interface.
 */
public interface UserUI<U extends User, R extends UserRepo> {

    /**
     * Displays the login screen and returns the user after a successful login.
     *
     * @param userRepo The repository of users to authenticate against.
     * @return The user who logged in.
     */
    U displayLogin(R userRepo);

    /**
     * Authenticates the user by comparing the provided credentials with the user repository.
     *
     * @param userRepo The repository of users to authenticate against.
     * @return The authenticated user.
     */
    U login(R userRepo);

    /**
     * Initiates the process for a user to recover their password.
     *
     * @param userRepo The repository of users to reset the password in.
     */
    void forgetPassword(R userRepo);

    /**
     * Displays the user's dashboard with options based on their profile and role.
     *
     * @param user The user whose dashboard will be displayed.
     */
    void displayDashboard(U user);

    /**
     * Initiates the process for a user to change their password.
     *
     * @param user The user whose password is being changed.
     */
    void changePassword(U user);

    /**
     * Default method that simulates exiting to the main menu.
     * Prints a message indicating that the user is returning to the menu.
     */
    default void exitToMenu(){
        System.out.println("Returning to Menu...");
        InputUtils.printBigDivider();
    }
}