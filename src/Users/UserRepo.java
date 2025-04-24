package Users;

/**
 * Generic interface for user data repository operations.
 * Handles loading, saving, and retrieving user data.
 *
 * @param <U> a subtype of User managed by the repository
 */
public interface UserRepo<U extends User> {

    /**
     * Loads user data from a persistent storage (e.g., file).
     */
    void loadFile();

    /**
     * Saves user data to persistent storage.
     */
    void saveFile();

    /**
     * Generates a unique ID for the user based on their NRIC.
     *
     * @param nric the NRIC string
     * @return a generated user ID
     */
    String generateID(String nric);

    /**
     * Adds a user to the repository.
     *
     * @param user the user to be added
     */
    void addUser(U user);

    /**
     * Retrieves a user from the repository by their ID.
     *
     * @param id the ID of the user
     * @return the user instance if found, otherwise null
     */
    U getUser(String id);
}
