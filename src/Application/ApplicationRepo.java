package Application;
import java.util.*;

/**
 * Generic interface for application repositories.
 * Defines the basic operations for storing and retrieving applications of any type.
 *
 * @param <T> The type of application this repository handles, must extend Application
 */
public interface ApplicationRepo<T extends Application>{
    /**
     * Adds a new application to the repository.
     *
     * @param application The application to add
     */
    void addApplication(T application);

    /**
     * Retrieves all applications stored in the repository.
     *
     * @return HashMap containing all applications mapped by their unique identifier
     */
    HashMap<String,T> getApplications();
}
