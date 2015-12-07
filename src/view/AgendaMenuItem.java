package view;

import Model.Agenda;
import javafx.scene.control.RadioMenuItem;

/**
 * Overridden radio menu item for assisting with switching agendas
 */
public class AgendaMenuItem extends RadioMenuItem {

    private Agenda myAgenda;

    public Agenda getMyAgenda() {
        return myAgenda;
    }

    public void setMyAgenda(Agenda myAgenda) {
        this.myAgenda = myAgenda;
        this.setText(myAgenda.getAgendaTitle()); //TODO necessary?

    }

    public AgendaMenuItem(Agenda agenda) {
        super();
        myAgenda = agenda;
        this.setText(myAgenda.getAgendaTitle());
    }

}
