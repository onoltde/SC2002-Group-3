package Users;

import java.util.Objects;

/**
 * Represents a user with details such as name, NRIC, age, marital status, and password.
 * Provides functionality for password validation, password reset, and displaying user details.
 */
public class User {

    /**
     * Enum representing the marital status of a user.
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
     * Constructs a new {@link User} object with the given details.
     *
     * @param name the name of the user
     * @param nric the NRIC of the user
     * @param age the age of the user
     * @param maritalStatus the marital status of the user
     * @param password the password for the user
     */
    public User(String name, String nric, int age, MaritalStatus maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    // Getters

    /**
     * Gets the name of the user.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the NRIC of the user.
     *
     * @return the user's NRIC
     */
    public String getNric() {
        return nric;
    }

    /**
     * Gets the age of the user.
     *
     * @return the user's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the marital status of the user.
     *
     * @return the user's marital status
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Gets the password of the user.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Changes the user's password.
     *
     * @param newPassword the new password to set
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Returns a string representation of the user, displaying the NRIC, age, and marital status.
     *
     * @return a string representing the user
     */
    @Override
    public String toString() {
        return "{NRIC='" + nric + "', Age=" + age + ", Marital Status=" + maritalStatus + "}";
    }

    /**
     * Validates the entered password against the user's stored password.
     *
     * @param enteredPassword the password to validate
     * @return true if the entered password matches the stored password, false otherwise
     */
    public boolean validatePassword(String enteredPassword) {
        return Objects.equals(this.password, enteredPassword);
    }

    /**
     * Resets the user's password to a default value of "password".
     */
    public void resetPassword() {
        this.password = "password";
    }
}
