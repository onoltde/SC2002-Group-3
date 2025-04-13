package Users;
public interface UserRepo<U extends User> {
    void loadFile();
    void saveFile();
    String generateID(String nric);
    void addUser(U user);
    U getUser(String id);
}
