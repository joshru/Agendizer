package Model;

import java.sql.Date;

/**
 * Created by Brandon on 11/30/2015.
 */
public class Task {

    private int taskID;
    private String taskTitle;
    private Date timeStamp;
    private int completed;
    private String difficulty;
    private String urgency;
    private String priority;
    private Date timeCompleted;
    private String notes;
    private String Location;
    private Integer fk_agendaID;


    // TODO get rid of urgency in Task
    public Task(int taskID, String title, Date timeStamp, int completed, String difficulty, String urgency,
                String priority, Date timeCompleted, String notes, String location, Integer fk_agendaID) {
        this.taskID = taskID;
        this.taskTitle = title;
        this.timeStamp = timeStamp;
        this.completed = completed;
        this.difficulty = difficulty;
        this.urgency = urgency;
        this.priority = priority;
        this.timeCompleted = timeCompleted;
        this.notes = notes;
        this.Location = location;
        this.fk_agendaID = fk_agendaID;
    }

    public String toString() {
        return (taskID + ", ") +
                taskTitle + ", " +
                timeStamp + ", " +
                completed + ", " +
                difficulty + ", " +
                urgency + ", " +
                priority + ", " +
                timeCompleted + ", " +
                notes + ", " +
                Location + ", " +
                fk_agendaID + ", ";

    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getTimeCompleted() {
        return timeCompleted;
    }

    public void setTimeCompleted(Date timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public Integer getFk_agendaID() {
        return fk_agendaID;
    }

    public void setFk_agendaID(Integer fk_agendaID) {
        this.fk_agendaID = fk_agendaID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

}
