package Model;

/**
 * Singleton state management class.
 */
public class Context {
    /**Singleton instance of this class*/
    private static Context instance;

    /** Currently logged in user ID*/
    private int currentUserID;
    /** Currently selected Agenda ID*/
    private int currentAgendaID;
    /** Currently selected Agenda name*/
    private String currentAgendaName;
    /** Current user's username*/
    private String currentUsername;

    /**
     * Obtains the instance of the context
     * @return current context
     */
    public static Context getInstance() {
        if (instance == null) instance = new Context();

        return instance;

    }

    public void switchUser() {
        currentUserID = 0;
        currentAgendaID = 0;
        currentAgendaName = null;
        currentUsername = null;
    }

    /**
     * Setter for the userID
     * @param theUser ID to reassign
     */
    public void setCurrentUserID(int theUser) {
        currentUserID = theUser;
    }

    /**
     * Getter for userID
     * @return current ID
     */
    public int getCurrentUserID() {
        return currentUserID;
    }

    /**
     * Setter for current Agenda ID
     * @param theAgenda ID to reassign
     */
    public void setCurrentAgendaID(int theAgenda) {
        currentAgendaID = theAgenda;
    }

    /**
     * Getter for current Agenda ID
     * @return current Agenda ID
     */
    public int getCurrentAgendaID() {
        return currentAgendaID;
    }

    /**
     * Getter for current Agenda name
     * @return the name
     */
    public String getCurrentAgendaName() {
        return currentAgendaName;
    }

    /**
     * Setter for current Agenda name
     * @param currentAgendaName to reassign
     */
    public void setCurrentAgendaName(String currentAgendaName) {
        this.currentAgendaName = currentAgendaName;
    }

    /*
     * Getter for the current username
     */
    public String getCurrentUsername() {
        return currentUsername;
    }

    /*
     * Setter for the current username
     * @param currentUsername to reassign
     */
    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }


}
