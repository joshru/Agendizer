package Databases;

import Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database adapter class for our User database.
 * @author Brandon Bell
 * @version 12/3/15
 */
public class UserDB extends DBHelper {

    /**
     * Creates and adds a user to the DB
     * @param theUser to be added
     * @return success flag
     */
    public boolean createUser(final User theUser) {
        String sql = "INSERT INTO _445team2.User (UserID, username, firstName, lastName, email, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps;

        try {
            if (myConnection == null) {
                createConnection();
            }
            ps = myConnection.prepareStatement(sql);
            ps.setInt(1, theUser.getUserID());
            ps.setString(2, theUser.getUsername());
            ps.setString(3, theUser.getFirstName());
            ps.setString(4, theUser.getLastName());
            ps.setString(5, theUser.getEmail());
            ps.setString(6, theUser.getPassword());

            int result = ps.executeUpdate();
            System.out.println("Update completed, result int: " + result);
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Checks if the user has inputted valid credentials
     * @param theUsername username
     * @param password password
     * @return success flag
     */
    public boolean hasAccount(final String theUsername, final String password) {
        boolean correctLogin = false;
        String sql = "SELECT * FROM _445team2.User WHERE username = ? AND password = ?;";


        PreparedStatement statement;

        try {
            if (myConnection == null) {
                createConnection();
            }
            statement = myConnection.prepareStatement(sql);
            statement.setString(1, theUsername);
            statement.setString(2, password);
            System.out.println(statement.toString());

            ResultSet res = statement.executeQuery();

            if (res.next() && res.getString("username").equals(theUsername)
                && res.getString("password").equals(password)) {
                correctLogin = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return correctLogin;
    }

}
