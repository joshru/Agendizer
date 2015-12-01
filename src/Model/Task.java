package Model;

import java.sql.Date;

/**
 * Created by Brandon on 11/30/2015.
 */
public class Task {

    Integer taskID;
    Date timeStamp;
    int completed;
    String category;
    String difficulty;
    String urgency;
    String priority;

    Date timeCompleted;
    String notes;

    String Location;

    Integer fk_agendaID;
    Integer fk_templateID;

    public Task(Integer taskID, Date timeStamp, int completed,
                String category, String difficulty, String urgency,
                String priority, Date timeCompleted, String notes,
                String location, Integer fk_agendaID, Integer fk_templateID) {
        this.taskID = taskID;
        this.timeStamp = timeStamp;
        this.completed = completed;
        this.category = category;
        this.difficulty = difficulty;
        this.urgency = urgency;
        this.priority = priority;
        this.timeCompleted = timeCompleted;
        this.notes = notes;
        Location = location;
        this.fk_agendaID = fk_agendaID;
        this.fk_templateID = fk_templateID;
    }












}
