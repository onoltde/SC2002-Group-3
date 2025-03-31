public class User {

    public enum MaritalStatus{
        SINGLE,
        MARRIED
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

    public String getName() { return name; }

    public String getNric() { return nric; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public MaritalStatus getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(MaritalStatus newStatus) { this.maritalStatus = newStatus ; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
