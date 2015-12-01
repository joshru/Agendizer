package Databases;

import Model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Brandon on 11/30/2015.
 */
public class TaskDB {

    private static String userName = "_445team2";
    private static String password = "poddoif";
    private static String serverName = "cssgate.insttech.washington.edu";
    private static Connection myConnection;
    private List<Task> taskList;


    public static void createConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        myConnection = DriverManager.getConnection("jdbc:" + "mysql" + "://" + serverName + "/", connectionProps);

        System.out.println("Connected to Databases.TaskDB");
    }

    public List<Task> getTasks() throws SQLException {
        if (myConnection == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "select * FROM " + userName + ".Task";
        taskList = new ArrayList<>();

        //Here's where the fun begins

        try {
            stmt = myConnection.createStatement();
            ResultSet results = stmt.executeQuery(query);

            while (results.next()) {
                String title = results.getString("title");
                int taskID = results.getInt("taskID");
                Date timestamp = results.getDate("timestamp");
                int completed = results.getInt("completed");
                String category = results.getString("category");
                String difficulty = results.getString("difficulty");
                String urgency = results.getString("urgency");
                String priority = results.getString("priority");
                Date timeCompleted = results.getDate("timeCompleted");
                String notes = results.getString("notes");
                String location = results.getString("location");
                int agendaID = results.getInt("agenda_agendaID");
                int templateID = results.getInt("tasktemplate_templateID");

                Task task = new Task(taskID, timestamp, completed, category, difficulty,
                        urgency, priority, timeCompleted, notes, location,
                        agendaID, templateID);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }


        return taskList;
    }

}
