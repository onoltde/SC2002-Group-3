public class User {
    private String name;
    private String nric;
    private String password;
    private int age;
    private MaritalStatus maritalStatus;

    public User(String name, String nric, int age, MaritalStatus maritalStatus) {
        this.name = name;
        this.nric = nric;
        this.password = "password"; 
        this.age = age;
        this.maritalStatus = maritalStatus;
    }
    
    public String getName(){
        return name;
    }

    public String getNric() {
        return nric;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

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
        return "User{NRIC='" + nric + "', Age=" + age + ", Marital Status=" + maritalStatus + "}";
    }

    //public abstract void printUserInfo();  
}
