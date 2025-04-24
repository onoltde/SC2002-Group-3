package Users;

import java.util.Objects;

/**
 * The {@code User} class serves as a base class for all users in the BTO Management System,
 * including {@code Applicant}, {@code HdbManager}, and {@code HdbOfficer}.
 * It stores user personal details such as name, NRIC, age, marital status, and password.
 * 
 * <p>This class also provides methods to access user details, validate credentials,
 * and perform password management actions such as changing or resetting the password.
 * 
 */
public class User {

    /**
     * Enumeration for marital status of a user.
     */
    public enum MaritalStatus {
        SINGLE,
        MARRIED;
    }

    private final String name;
    private final String nric;
    private int age;
    private MaritalStatus maritalStatus;
    private String password;

    /**
     * Constructs a new {@code User} with the specified details.
     *
     * @param name the user's full name
     * @param nric the user's NRIC
     * @param age the user's age
     * @param maritalStatus the user's marital status
     * @param password the user's password
     */
    public User(String name, String nric, int age, MaritalStatus maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    /** @return the user's name */
    public String getName() { return name; }

    /** @return the user's NRIC */
    public String getNric() { return nric; }

    /** @return the user's age */
    public int getAge() { return age; }

    /** @return the user's marital status */
    public MaritalStatus getMaritalStatus() { return maritalStatus; }

    /** @return the user's password */
    public String getPassword() { return password; }

    /**
     * Changes the user's password to a new one.
     *
     * @param newPassword the new password to set
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Returns a string representation of the user object excluding sensitive information.
     *
     * @return a summary string of the user
     */
    @Override
    public String toString() {
        return "{NRIC='" + nric + "', Age=" + age + ", Marital Status=" + maritalStatus + "}";
    }

    /**
     * Validates the input password against the stored password.
     *
     * @param enteredPassword the password to check
     * @return {@code true} if the entered password matches the stored password; {@code false} otherwise
     */
    public boolean validatePassword(String enteredPassword) {
        return Objects.equals(this.password, enteredPassword);
    }

    /**
     * Resets the user's password to a default value.
     */
    public void resetPassword() {
        this.password = "password";
    }
}
