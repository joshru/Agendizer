package Databases;

import Model.Agenda;
import Model.Context;
import Model.Task;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Database adapter class for our Agenda database.
 * @author Brandon Bell
 * @version 12/3/15
 */
public class AgendaDB extends DBHelper {

    /**List of Agendas*/
    private List<Agenda> agendaList;
    /**List of Agendas that can be placed into table view */
    private ObservableList<Agenda> obsAgendaList;

    /**Helper flag for populateList()*/
    boolean gettingUserAgendas; //dirty
    /**Helper flag for populateList()*/
    boolean gettingTitleAgenda; //also dirty


    /**
     * Obtains the entire list of Agendas
     * @return list of agendas
     * @throws SQLException
     */
    public List<Agenda> getAgendas() throws SQLException {
        if (myConnection == null) {
            createConnection();
        }
        String query = "select * FROM " + userName + ".Agenda";
        agendaList = new ArrayList<>();
        obsAgendaList = FXCollections.observableArrayList();

        populateList(query);

        return agendaList;


    }

    /**
     * Obtains a specific agenda by its title
     * @return the Agenda
     * @throws SQLException
     */
    public Agenda getAgendaByTitle() throws SQLException { //TODO change me back to requiring a string if this breaks
        if (myConnection == null) {
            createConnection();
        }
        String query = "select * from " + userName + ".Agenda WHERE " + userName + ".Agenda.title = ?;";

        //Clear out lists
        agendaList = new ArrayList<>();
        obsAgendaList = FXCollections.observableArrayList();

        //Set flag to let populateList know which arguments to pass to the statement
        gettingTitleAgenda = true;
        populateList(query);
        gettingTitleAgenda = false;

        Agenda result = null;

        //Return the Agenda if it exists
        if (!obsAgendaList.isEmpty()) {
            obsAgendaList.get(0);
            System.out.println("Retrieved agenda by title Expected: " + Context.getInstance().getCurrentAgendaName()
            + " Actual: " + obsAgendaList.get(0).getAgendaTitle());
        }

        return result;



    }

   /* public Agenda getAgendaByTitle(String title) throws SQLException {
        getUserAgendas();
        Agenda result = null;

        Iterator<Agenda> itr = obsAgendaList.iterator();
        //result = obsAgendaList.stream().filter(e -> title.equalsIgnoreCase(e.getAgendaTitle()) ).findFirst();
        boolean found = false;

        while (!found && itr.hasNext()) {
            Agenda current = itr.next();
            if (current.getAgendaTitle().equals(title)) {
                found = true;
                result = current;
            }
        }

        return result;
    }*/

    /**
     * Gets the agendas for the current user
     * @return The Agenda list
     */
    public List<Agenda> getUserAgendas() {
        if (myConnection == null) {
            createConnection();
        }
        String query = "SELECT * FROM " + userName + ".Agenda WHERE " + userName + ".Agenda.fkUser = ?;";
        agendaList = new ArrayList<>();
        obsAgendaList = FXCollections.observableArrayList();

        //Set flag to let populateList know what arguments to pass into the statement
        gettingUserAgendas = true;

       // try {
            populateList(query);
        // } catch (SQLException e) {
       //     e.printStackTrace();
      //  }
        gettingUserAgendas = false; //reset flag

        return agendaList;

    }

    /**
     * Populates the list of agendas given a query
     * @param query to be executed on the DB
     */
    private void populateList(String query) {
        PreparedStatement stmt = null;


        try {
            createConnection(); //TODO is this necessary? It should happen in the calling method
            stmt = myConnection.prepareStatement(query);

            //Super duper dirty way of doing this //TODO This actually doesn't seem TOO dirty...
            if (gettingUserAgendas) stmt.setInt(1, Context.getInstance().getCurrentUserID());
            if (gettingTitleAgenda) stmt.setString(1, Context.getInstance().getCurrentAgendaName());

            System.out.println(stmt.toString());

            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                int agendaID = results.getInt("agendaID");
                String agendaTitle = results.getString("title");
                int fk_userID = results.getInt("fkUser");

                Agenda agenda = new Agenda(agendaID, agendaTitle, fk_userID);
                System.out.println("Agenda created: " + agendaTitle);

                agendaList.add(agenda);
                obsAgendaList.add(agenda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                //TODO take a bath
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Adds an Agenda to the DB
     * @param agenda to be added
     */
    public void createAgenda(Agenda agenda) {
        String statement = "INSERT INTO _445team2.Agenda VALUES (?, ?, ?)";
        PreparedStatement preparedStatement;

        try {
            createConnection();
            preparedStatement = myConnection.prepareStatement(statement);

            preparedStatement.setInt(1, agenda.getAgendaID());
            preparedStatement.setString(2, agenda.getAgendaTitle());
            preparedStatement.setInt(3, agenda.getFk_userID());

            int result = preparedStatement.executeUpdate();
            System.out.println("Agenda creation code " + result);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
