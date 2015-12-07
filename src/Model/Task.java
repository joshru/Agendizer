package Model;

import java.sql.Date;

/**
 * Model wrapper for Task database entry type.
 * @author Brandon Bell
 * @version 12/6/15
 */
public class Task {
    /**ID primary key*/
    private int taskID;
    /**Task Title*/
    private String taskTitle;
    /**Deadline timeStamp*/
    private Date timeStamp;
    /**Completed flag (DB doesn't support boolean) Will always be 0 or 1*/
    private int completed;
    /**Difficulty string*/
    private String difficulty;
    /**Priority String*/
    private String priority;
    /**Time completed timestamp*/
    private Date timeCompleted;
    /**User notes for this task*/
    private String notes;
    /** Optional location field*/
    private String Location;
    /** Foreign key agenda ID*/
    private Integer fk_agendaID;


    // TODO get rid of urgency in Task

    /**
     * Constructor
     * @param taskID ID
     * @param title Title
     * @param timeStamp Deadline timestamp
     * @param completed completion flag
     * @param difficulty difficulty indicator
     * @param priority priority indicator
     * @param timeCompleted completed timestamp
     * @param notes user notes
     * @param location info
     * @param fk_agendaID Agenda Foreign key
     */
    public Task(int taskID, String title, Date timeStamp, int completed, String difficulty,
                String priority, Date timeCompleted, String notes, String location, Integer fk_agendaID) {
        this.taskID = taskID;
        this.taskTitle = title;
        this.timeStamp = timeStamp;
        this.completed = completed;
        this.difficulty = difficulty;
        this.priority = priority;
        this.timeCompleted = timeCompleted;
        this.notes = notes;
        this.Location = location;
        this.fk_agendaID = fk_agendaID;
    }

    /**
     * Obtains a string representation of this Task
     * @return the string
     */
    public String toString() {
        return (taskID + ", ") +
                taskTitle + ", " +
                timeStamp + ", " +
                completed + ", " +
                difficulty + ", " +
                priority + ", " +
                timeCompleted + ", " +
                notes + ", " +
                Location + ", " +
                fk_agendaID + ", ";

    }

    /**
     * Getter for Title
     * @return title
     */
    public String getTaskTitle() {
        return taskTitle;
    }

    /**
     * Setter for title
     * @param taskTitle to be reassigned
     */
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    /**
     * Getter for deadline timestamp
     * @return the timestamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * Setter for deadline timestamp
     * @param timeStamp to be reassigned
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Getter for completion flag
     * @return completion flag
     */
    public int getCompleted() {
        return completed;
    }

    /**
     * Setter for completion flag
     * @param completed flag to reassign
     */
    public void setCompleted(int completed) {
        this.completed = completed;
    }

    /**
     * Getter for difficulty value
     * @return difficulty
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Setter for difficulty value
     * @param difficulty to reassign
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Getter for priority value
     * @return priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Setter for priority value
     * @param priority to reassign
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Getter for completion timestamp
     * @return timeCompleted
     */
    public Date getTimeCompleted() {
        return timeCompleted;
    }

    /**
     * Setter for time completed value
     * @param timeCompleted to reassign
     */
    public void setTimeCompleted(Date timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    /**
     * Getter for user notes
     * @return user notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Setter for user notes
     * @param notes to reassign
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Getter for location value
     * @return location
     */
    public String getLocation() {
        return Location;
    }

    /**
     * Setter for location value
     * @param location to reassign
     */
    public void setLocation(String location) {
        Location = location;
    }

    /**
     * Getter for agenda foreign key
     * @return agendaID foreign key
     */
    public Integer getFk_agendaID() {
        return fk_agendaID;
    }

    /**
     * Setter for agenda foreign key
     * @param fk_agendaID foreign key to reassign
     */
    public void setFk_agendaID(Integer fk_agendaID) {
        this.fk_agendaID = fk_agendaID;
    }

    /**
     * Getter for taskID
     * @return taskID
     */
    public int getTaskID() {
        return taskID;
    }

    /**
     * Setter for taskID value
     * @param taskID to reassign
     */
    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

}
