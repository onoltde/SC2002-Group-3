package Users;

/**
 * Controllers implementing this interface must handle the user portal logic and file persistence.
 */
public interface UserController {

    /**
     * Runs the main portal interface for the user.
     */
    void runPortal();

    /**
     * Saves the current user data to file.
     */
    void saveFile();
}
