public class User {

    private final String name;
    private final String nric;
    private int age;
    private MaritalStatus maritalStatus;
    private String password;


    public User (String name,String nric, int age, MaritalStatus maritalStatus){
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = "password";
    }
}
