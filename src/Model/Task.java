package Model;

import java.sql.Date;

/**
 * Created by Brandon on 11/30/2015.
 */
public class Task {

    Integer taskID;
    String taskTitle;
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

    public Task(Integer taskID, String title, Date timeStamp, int completed,
                String category, String difficulty, String urgency,
                String priority, Date timeCompleted, String notes,
                String location, Integer fk_agendaID, Integer fk_templateID) {
        this.taskID = taskID;
        this.taskTitle = title;
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(taskID + ", ");
        sb.append(taskTitle + ", ");
        sb.append(timeStamp + ", ");
        sb.append(completed + ", ");
        sb.append(category + ", ");
        sb.append(difficulty + ", ");
        sb.append(urgency + ", ");
        sb.append(priority + ", ");
        sb.append(timeCompleted + ", ");
        sb.append(notes + ", ");
        sb.append(Location + ", ");
        sb.append(fk_agendaID + ", ");
        sb.append(fk_templateID + " ");
        return sb.toString();

    }











}
