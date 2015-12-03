package Databases;

import Model.Task;
import Model.User;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by Josh Rueschenberg on 12/2/2015.
 */
public class UserDB {
    private static String userName = "_445team2";
    private static String password = "poddoif";
    private static String serverName = "cssgate.insttech.washington.edu";
    private static Connection myConnection;
    private List<Task> taskList;
    private ObservableList<Task> obsTasks;

    public void createConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        myConnection = DriverManager.getConnection("jdbc:" + "mysql" + "://" + serverName + "/", connectionProps);

        System.out.println("Connected to Databases.UserDB");
    }

    public void createUser(final User theUser) {
        String sql = "INSERT INTO _445team2.User (UserID, username, firstName, lastName, email, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = null;

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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public boolean hasAccount(final String theUsername, final String password) {
        boolean correctLogin = false;
        String sql = "SELECT * FROM _445team2.User WHERE username = ? AND password = ?;";


        PreparedStatement statement = null;

        try {
            if (myConnection == null) {
                createConnection();
            }
            statement = myConnection.prepareStatement(sql);
            statement.setString(1, theUsername);
            statement.setString(2, password);
            System.out.println(statement.toString());

            ResultSet results = statement.executeQuery();
            System.out.println("size of user results = " + results.getFetchSize());
            if (results.next() && results.getString("username").equals(theUsername) && results.getString("password").equals(password)) {
                correctLogin = true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return correctLogin;
    }

}
