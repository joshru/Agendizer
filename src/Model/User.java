package Model;

import java.util.Date;

/**
 * Model Wrapper for User tuple entries
 * @author Josh Rueschenberg
 * @version 1337
 */
public class User {

    /**User ID*/
    private int userID;
    /**Username*/
    private String username;
    /**User first name*/
    private String firstName;
    /**User last name*/
    private String lastName;
    /**User email*/
    private String email;
    /**User password (super secure)*/
    private String password;

    /**
     * Constructor
     * @param userID ID for this user
     * @param username for this user
     * @param firstName of this user
     * @param lastName of this user
     * @param email belonging to this user
     * @param password for this user
     */
    public User(int userID, String username, String firstName, String lastName,
                String email, String password) {
        this.userID = userID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Getter for user ID
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter for user ID
     * @param userID to reassign
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for user name
     * @return userName
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username
     * @param username to reassign
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for user first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for user last name
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for user email address
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for user email address
     * @param email to reassign
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for user password (super duper secure)
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for user password
     * @param password to reassign
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Creates a string representation of this user (Ultra mega secure)
     * @return the String
     */
    @Override
    public String toString() {
        return (userID + ", ") +
                username + ", " +
                firstName + ", " +
                lastName + ", " +
                email + ", " +
                password;
    }
}
