package Users;

/**
 * Interface representing a controller for managing user-related actions in the system.
 * Provides methods to run the user portal and save user data to a file.
 */
public interface UserController {

    /**
     * Runs the user portal, allowing users to interact with the system.
     */
    void runPortal();

    /**
     * Saves user data to a file.
     */
    void saveFile();
}
