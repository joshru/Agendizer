package Model;

import java.sql.Date;

/**
 * Created by Brandon on 11/30/2015.
 */
public class Task {

    int taskID;
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



    public Task(int taskID, String title, Date timeStamp, int completed,
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Integer getFk_templateID() {
        return fk_templateID;
    }

    public void setFk_templateID(Integer fk_templateID) {
        this.fk_templateID = fk_templateID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }









}
