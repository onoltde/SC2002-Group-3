package Application;
import java.util.*;

public interface ApplicationRepo<T extends Application>{
    void addApplication(T application);

    HashMap<String,T> getApplications();

}
