package Databases;

import Model.Agenda;
import Model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Database adapter class for our Task database.
 * @author Brandon Bell
 * @version 12/3/15
 */
public class TaskDB extends DBHelper {

//    private static String userName = "_445team2";
//    private static String password = "poddoif";
//    private static String serverName = "cssgate.insttech.washington.edu";
//   // private static Connection myConnection;
//    private List<Task> taskList;
    private ObservableList<Task> obsTasks;




    public ObservableList<Task> getTasks() throws SQLException {
        if (myConnection == null) {
            createConnection();
        }
        String query = "select * FROM " + userName + ".Task;";
        //taskList = new ArrayList<>();
        obsTasks = FXCollections.observableArrayList();

        //Here's where the fun begins
        parseResultSet(query);


        //System.out.println(taskList.toString());
        return obsTasks;
    }



    public ObservableList<Task> getAgendaTasks(Agenda agenda) throws SQLException {
        if (myConnection == null) {
            createConnection();
        }

        String query = "select * FROM " + userName + ".Task WHERE " + userName +" .Task.agenda_agendaID = " + agenda.getAgendaID();
        obsTasks = FXCollections.observableArrayList();

        parseResultSet(query);


        return obsTasks;
    }

    public void addTask(Task task) {
        String statement = "INSERT INTO _445team2.Task (taskID, title, timestamp, completed, difficulty," +
                           "urgency, priority, timeCompleted, notes, location, agenda_agendaID) VALUES " +
                           "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement;

        try {
            createConnection();
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

    private void parseResultSet(String query) throws SQLException {
        Statement stmt = null;


        try {
            stmt = myConnection.createStatement();
            ResultSet results = stmt.executeQuery(query);

            while (results.next()) {
                int taskID = results.getInt("taskID");
                String title = results.getString("title");
                Date timestamp = results.getDate("timestamp");
                int completed = results.getInt("completed");
//                String category = results.getString("category");
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
                //taskList.add(task);
                obsTasks.add(task);

                System.out.println("Grabbed Task  = " + task.toString());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }


    }

}
