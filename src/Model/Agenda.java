package Model;

import java.sql.Date;

/**
 * Agenda model wrapper object
 * @author Brandon Bell
 * @version 12/6/15
 */
public class Agenda {

    /** Agenda ID PK*/
    private int agendaID;
    /**Agenda Title*/
    private String agendaTitle;
    /**Expires flag */ //TODO delete me
    private int expires;
    /**Expiration time for this agenda*/ //TODO delete me
    private Date expireTime;
    /**userID foreign key */
    private int fk_userID;


    /**
     * Constructor
     * @param agendaID ID
     * @param agendaTitle Title
     * @param fk_userID foreign key UserID
     */
    public Agenda(int agendaID, String agendaTitle, int fk_userID) {
        this.agendaID = agendaID;
        this.agendaTitle = agendaTitle;
        this.fk_userID = fk_userID;
    }

    /**
     * Getter for Agenda ID
     * @return ID
     */
    public int getAgendaID() {
        return agendaID;
    }

    /**
     * Setter for Agenda ID
     * @param agendaID to assign
     */
    public void setAgendaID(int agendaID) {
        this.agendaID = agendaID;
    }

    /**
     * Getter for Agenda title
     * @return Title
     */
    public String getAgendaTitle() {
        return agendaTitle;
    }

    /**
     * Setter for Agenda title
     * @param agendaTitle to reassign
     */
    public void setAgendaTitle(String agendaTitle) {
        this.agendaTitle = agendaTitle;
    }

    /**
     * Getter for userID foreign key
     * @return the foreign key
     */
    public int getFk_userID() {
        return fk_userID;
    }

    /**
     * Setter for userID foreign key
     * @param fk_userID the updated key
     */
    public void setFk_userID(int fk_userID) {
        this.fk_userID = fk_userID;
    }
}
