package Databases;

import Model.Agenda;
import Model.Task;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Database adapter class for our Agenda database.
 * @author Brandon Bell
 * @version 12/3/15
 */
public class AgendaDB extends DBHelper {


    private List<Agenda> agendaList;




    public List<Agenda> getAgendas() throws SQLException {
        if (myConnection == null) {
            createConnection();
        }
        String query = "select * FROM " + userName + ".Agenda";
        agendaList = new ArrayList<>();

        populateList(query);
//        try {
//
//            stmt = myConnection.createStatement();
//            ResultSet results = stmt.executeQuery(query);
//
//            while (results.next()) {
//                int agendaID = results.getInt("agendaID");
//                String agendaTitle = results.getString("title");
//                int fk_userID = results.getInt("fkUser");
//
//                Agenda agenda = new Agenda(agendaID, agendaTitle, fk_userID);
//                System.out.println("Agenda created");
//
//                agendaList.add(agenda);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (stmt != null) {
//                stmt.close();
//            }
//        }
        return agendaList;


    }

    public List<Agenda> getUserAgendas(User user) throws SQLException {
        if (myConnection == null) {
            createConnection();
        }
        String query = "select * FROM " + userName + ".Agenda WHERE " + userName + ".User.UserID = " + user.getUserID();
        agendaList = new ArrayList<>();

        populateList(query);

        return agendaList;

    }

    private void populateList(String query) throws SQLException {
        Statement stmt = null;


        try {

            stmt = myConnection.createStatement();
            ResultSet results = stmt.executeQuery(query);

            while (results.next()) {
                int agendaID = results.getInt("agendaID");
                String agendaTitle = results.getString("title");
                int fk_userID = results.getInt("fkUser");

                Agenda agenda = new Agenda(agendaID, agendaTitle, fk_userID);
                System.out.println("Agenda created");

                agendaList.add(agenda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }



    public void createAgenda(Agenda agenda) {
        String statement = "INSERT INTO _445team2.Agenda VALUES " +
                "(?, ?, ?)";
        PreparedStatement preparedStatement;

        try {
            createConnection();
            preparedStatement = myConnection.prepareStatement(statement);

            preparedStatement.setInt(1, agenda.getAgendaID());
            preparedStatement.setString(2, agenda.getAgendaTitle());
            preparedStatement.setInt(3, agenda.getFk_userID());

            int result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
