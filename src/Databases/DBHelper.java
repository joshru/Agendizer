package Databases;
import java.sql.*;
import java.util.Properties;

/**
 * Abstract class used to handle common behavior between the databases.
 * @author Brandon Bell
 * @version 12/3/2015
 */
abstract class DBHelper {
    /**Team MySQL username*/
    static final String userName = "_445team2";
    /**Team MySQL password*/
    private static final String password = "poddoif";
    /**cssgate server URL*/
    private static final String serverName = "cssgate.insttech.washington.edu";
    /**Connection to the server*/
    static Connection myConnection;

    /**
     * establishes a connection with the team Database
     */
    void createConnection()  {
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        try {
            myConnection = DriverManager.getConnection("jdbc:" + "mysql" + "://" + serverName + "/", connectionProps);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Connected to Databases.UserDB");
    }



}
