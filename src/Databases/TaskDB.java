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

                Task task = new Task(taskID, title, timestamp, completed, difficulty,
                                     urgency, priority, timeCompleted, notes, location, agendaID);

                System.out.println("created a task");
                taskList.add(task);

                System.out.println("Grabbed Task  = " + task.toString());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        System.out.println(taskList.toString());
        return taskList;
    }

    public void addTask(Task task) {
        String statement = "INSERT INTO _445team2.Task (taskID, title, timestamp, completed, category, difficulty," +
                           "urgency, priority, timeCompleted, notes, location," + "agenda_agendaID) VALUES " +
                           "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println(statement);
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = myConnection.prepareStatement(statement);
            preparedStatement.setInt(1, task.getTaskID());
            preparedStatement.setString(2, task.getTaskTitle());
            preparedStatement.setDate(3, task.getTimeStamp());
            preparedStatement.setInt(4, task.getCompleted());
            preparedStatement.setString(5, task.getDifficulty());
            preparedStatement.setString(6, task.getUrgency());
            preparedStatement.setString(7, task.getPriority());
            preparedStatement.setDate(8, task.getTimeCompleted());
            preparedStatement.setString(9, task.getNotes());
            preparedStatement.setString(10, task.getLocation());
            preparedStatement.setInt(11, task.getFk_agendaID());

            int result = preparedStatement.executeUpdate();

            System.out.println("Update complete. Result int: " + result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
