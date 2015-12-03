package Model;

import java.sql.Date;

/**
 * Created by Brandon on 12/2/2015.
 */
public class Agenda {


    private int agendaID;
    private String agendaTitle;
    private int expires;
    private Date expireTime;
    private int fk_userID;



    public Agenda(int agendaID, String agendaTitle, int fk_userID) {
        this.agendaID = agendaID;
        this.agendaTitle = agendaTitle;
        this.fk_userID = fk_userID;
    }

    public int getAgendaID() {
        return agendaID;
    }

    public void setAgendaID(int agendaID) {
        this.agendaID = agendaID;
    }

    public String getAgendaTitle() {
        return agendaTitle;
    }

    public void setAgendaTitle(String agendaTitle) {
        this.agendaTitle = agendaTitle;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public int getFk_userID() {
        return fk_userID;
    }

    public void setFk_userID(int fk_userID) {
        this.fk_userID = fk_userID;
    }
}
