package Model;

/**
 * Created by Brandon on 12/4/2015.
 */
public class Context {

    private static Context instance;

    private int currentUserID; // = new Context();
    private int currentAgendaID;

    public static Context getInstance() {
        if (instance == null) instance = new Context();

        return instance;

    }

    public void setCurrentUserID(int theUser) {
        currentUserID = theUser;
    }

    public int getCurrentUserID() {
        return currentUserID;
    }

    public void setCurrentAgendaID(int theAgenda) {
        currentAgendaID = theAgenda;
    }

    public int getCurrentAgenda() {
        return currentAgendaID;
    }

}
