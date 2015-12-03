package Databases;

import Model.Agenda;
import Model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Brandon on 12/2/2015.
 */
public class AgendaDB {

    private static String userName = "_445team2";
    private static String password = "poddoif";
    private static String serverName = "cssgate.insttech.washington.edu";
    private static Connection myConnection;
    private List<Agenda> agendaList;


    public static void createConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        myConnection = DriverManager.getConnection("jdbc:" + "mysql" + "://" + serverName + "/", connectionProps);

        System.out.println("Connected to Databases");
    }

    public List<Agenda> getAgendas() throws SQLException {
        if (myConnection == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "select * FROM " + userName + ".Agenda";
        agendaList = new ArrayList<>();

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
        return agendaList;

    }

    public void createAgenda(Agenda agenda) {
        String statement = "INSERT INTO _445team2.Agenda VALUES " +
                "(?, ?, ?)";
        PreparedStatement preparedStatement = null;

        try {
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
