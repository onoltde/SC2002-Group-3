package Users;

/**
 * Interface representing a repository for storing and managing users.
 * Provides methods to load and save user data, generate user IDs, and retrieve users.
 *
 * @param <U> The type of user this repository will manage, extending the {@link User} class.
 */
public interface UserRepo<U extends User> {

    /**
     * Loads user data from a file.
     * This method should retrieve the saved user data and populate the repository.
     */
    void loadFile();

    /**
     * Saves user data to a file.
     * This method should persist the current user data to a file for future retrieval.
     */
    void saveFile();

    /**
     * Generates a unique ID based on the provided NRIC.
     *
     * @param nric The NRIC of the user to generate the ID for.
     * @return A unique ID for the user based on their NRIC.
     */
    String generateID(String nric);

    /**
     * Adds a user to the repository.
     *
     * @param user The user to be added to the repository.
     */
    void addUser(U user);

    /**
     * Retrieves a user from the repository by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user associated with the provided ID, or {@code null} if no user is found.
     */
    U getUser(String id);
}