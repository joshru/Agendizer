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


    /** List of upcoming tasks*/
    private ObservableList<Task> upcomingTasks;
    /** List of completed tasks*/
    private ObservableList<Task> completedTasks;


    /**
     * Obtains a list of upcoming tasks
     * @return upcoming tasks
     */
    public ObservableList<Task> getUpcomingTasks()  {
        return getSpecifiedTask(true);
    }

    /**
     * Obtains a list of completed tasks
     * @return completed tasks

     */
    public ObservableList<Task> getCompletedTasks()  {
        return getSpecifiedTask(false);
    }

    /**
     * Obtains tasks for a given user
     * @param upcoming flag to determine whether to retrieve upcoming or completed tasks
     * @return List of tasks
     */
    private ObservableList<Task> getSpecifiedTask(final boolean upcoming) {
        if (myConnection == null) {
            createConnection();
        }
        //TODO do we need to specify which user we're getting tasks for?
        String query = "";
        if (upcoming) query = "SELECT * FROM " + userName + ".Task WHERE completed = 0;";
        if (!upcoming) query = "SELECT * FROM " + userName + ".Task WHERE completed = 1;";
        upcomingTasks = FXCollections.observableArrayList();


            parseResultSet(query);

        return upcomingTasks; //TODO should we have a conditional here to determine which list to return?
    }

    /**
     * Marks a given task as complete.
     * @param complete task to flip the complete value

     */
    public void completeTask(Task complete) {
        if (myConnection == null) {
            createConnection();
        }
        String query = "UPDATE `_445team2`.Task SET completed = 1 WHERE taskID = ?;";
        PreparedStatement ps;

        try {
            ps = myConnection.prepareStatement(query);
            ps.setInt(1, complete.getTaskID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Updates a task entry in the DB
     * @param column to update
     * @param newVal to set column value to
     * @param id of the task being affected
     */
    public void updateTask(final String column, final String newVal, final int id) {
        if (myConnection == null) {
            createConnection();
        }


        String query = "UPDATE `_445team2`.Task SET " + column + " = ? WHERE taskID = ?;";

        PreparedStatement ps;

        try {
            ps = myConnection.prepareStatement(query);

            ps.setString(1, newVal);
            ps.setInt(2, id);

            System.out.println(ps.toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the tasks for a given agenda.
     * @param agenda to retrieve tasks for
     * @param completed flag determining whether to return completed or upcoming tasks
     * @return list of tasks
     */
    public ObservableList<Task> getAgendaTasks(Agenda agenda, boolean completed) {
        if (myConnection == null) {
            createConnection();
        }

        String query = "select * FROM " + userName + ".Task WHERE " + userName +" .Task.agenda_agendaID = " + agenda.getAgendaID();
        upcomingTasks = FXCollections.observableArrayList();
        completedTasks = FXCollections.observableArrayList();

        parseResultSet(query);


        if (!completed) return upcomingTasks;
        return completedTasks;
//        return parseResultSet(query);
    }

    /**
     * Removes task from the DB
     * @param delete task to be deleted
     */
    public void removeTask(Task delete) {
        String sql = "DELETE FROM _445team2.Task WHERE taskID = ?";
        PreparedStatement ps;
        try {
            createConnection();
            ps = myConnection.prepareStatement(sql);
            ps.setInt(1, delete.getTaskID());
            int result = ps.executeUpdate();
            System.out.println("deleted statement. result = " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds a task to the database
     * @param task to be added
     */
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
            e.printStackTrace();
        }
    }

    /**
     * Populates the lists of upcoming and completed tasks given a query
     * @param query to be executed on the DB
     */
    private void parseResultSet(String query) {
        Statement stmt = null;


        try {
            stmt = myConnection.createStatement();
            ResultSet results = stmt.executeQuery(query);

            while (results.next()) {
                int taskID = results.getInt("taskID");
                String title = results.getString("title");
                Date timestamp = results.getDate("timestamp");
                int completed = results.getInt("completed");
                String difficulty = results.getString("difficulty");
                String urgency = results.getString("urgency");
                String priority = results.getString("priority");
                Date timeCompleted = results.getDate("timeCompleted");
                String notes = results.getString("notes");
                String location = results.getString("location");
                int agendaID = results.getInt("agenda_agendaID");

                Task task = new Task(taskID, title, timestamp, completed, difficulty,
                        urgency, priority, timeCompleted, notes, location, agendaID);


                if (completed == 0) upcomingTasks.add(task);
                if (completed == 1) completedTasks.add(task);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                //Don't like the nested try/catch, but it's better than throwing it in the method header
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
