package Databases;
import java.sql.*;
import java.util.Properties;

/**
 * Abstract class used to handle common behavior between the databases.
 * @author Brandon Bell
 * @version 12/3/2015
 */
public abstract class DBHelper {

    protected static String userName = "_445team2";
    protected static String password = "poddoif";
    protected static String serverName = "cssgate.insttech.washington.edu";
    protected static Connection myConnection;

    /**
     * establishes a connection with the team Database
     * @throws SQLException
     */
    public void createConnection()  {
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
