package Users;
import java.util.Objects;

public class User {

    public enum MaritalStatus{
        SINGLE,
        MARRIED;
    }

    private final String name;
    private final String nric;
    private int age;
    private MaritalStatus maritalStatus;
    private String password;


    public User (String name,String nric, int age, MaritalStatus maritalStatus, String password){
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }
    //getters
    public String getName() { return name; }
    public String getNric() { return nric; }
    public int getAge() { return age; }
    public MaritalStatus getMaritalStatus() { return maritalStatus; }
    public String getPassword() { return password; }
    //setters
    public void setMaritalStatus(MaritalStatus newStatus) { this.maritalStatus = newStatus ; }
    public void setAge(int age) { this.age = age; }
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean login(String nricInput, String passwordInput) {
        if (this.nric.equals(nricInput) && this.password.equals(passwordInput)) {
            return true;
        }
        return false;
    }

    public static boolean isValidNric(String nric) {
        return nric.matches("[ST]\\d{7}[A-Z]");
    }

    @Override
    public String toString() {
        return "{NRIC='" + nric + "', Age=" + age + ", Marital Status=" + maritalStatus + "}";
    }

    public boolean validatePassword(String enteredPassword) {
        return Objects.equals(this.password, enteredPassword);
    }

    public void resetPassword(){
        this.password = "password";
    }
}