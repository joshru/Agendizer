package Controller;

import Databases.AgendaDB;
import Databases.TaskDB;
import Model.Agenda;
import Model.Context;
import Model.Task;
import javafx.collections.FXCollections;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.AgendaMenuItem;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the primary GUI components.
 * @author Josh Rueschenberg
 * @version 12/6/15
 */
public class AppController implements Initializable {

    /**
     *
     * All fields with @FXML tag are references to GUI elements defined in
     * the FXML files located in the /view package.
     *
     */
    @FXML private Parent guiRoot;

    @FXML private Label currentUser;

    @FXML private Label upcomingTaskError;
    @FXML private Label completedTaskError;
    @FXML private TableView<Task> upcomingTaskTable;
    @FXML private TableView<Task> completedTaskTable;


    @FXML private Menu AgendasMenu;

    // these columns should probably be in an array or something. messy
    @FXML private TableColumn<Task, String> ucTaskCol;
    @FXML private TableColumn<Task, String> ucDeadlineCol;
    @FXML private TableColumn<Task, String> ucDifficultyCol;
    @FXML private TableColumn<Task, String> ucNotesCol;
    @FXML private TableColumn<Task, String> ucPriorityCol;
    @FXML private TableColumn<Task, String> compTaskCol;
    @FXML private TableColumn<Task, String> compDeadlineCol;
    @FXML private TableColumn<Task, String> compDifficultyCol;
    @FXML private TableColumn<Task, String> compNotesCol;
    @FXML private TableColumn<Task, String> compPriorityCol;

    @FXML private TextField newTaskDescription;
    @FXML private TextField newTaskNotes;
    @FXML private DatePicker newTaskDeadline;
    @FXML private ChoiceBox newTaskDifficulty;
    @FXML private ChoiceBox newTaskPriority;

    @FXML private TextField newAgendaName;


    private ObservableList<Task> taskList;


    /**Group for toggle buttons in the agenda drop down menu*/
    private ToggleGroup agendaGroup;
    /** Reference to task DB */
    private static final TaskDB db = new TaskDB();
    /** Reference to Agenda database*/
    private static final AgendaDB adb = new AgendaDB();


    /**
     * Runs on startup. Used to initialize GUI elements/assign listeners as appropriate
     * @param location -
     * @param resources -
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //when setting property values the string must MATCH the field name in the Task class
        ucTaskCol.setCellValueFactory(new PropertyValueFactory<>("taskTitle"));
        ucDeadlineCol.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        ucNotesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        ucDifficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        ucPriorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));

        compTaskCol.setCellValueFactory(new PropertyValueFactory<>("taskTitle"));
        compDeadlineCol.setCellValueFactory(new PropertyValueFactory<>("timeCompleted"));
        compNotesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        compDifficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        compPriorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));

        currentUser.setText("Current User:  " + Context.getInstance().getCurrentUsername());
        agendaGroup = new ToggleGroup();

        upcomingTaskTable.setPlaceholder(new Label("Please select an agenda or create a new task."));
        completedTaskTable.setPlaceholder(new Label("Please select an agenda."));

        taskList = FXCollections.observableArrayList();

        changeErrorLabels();
        setOnEditCommitHandlers();
        populateAgendaList();
    }

    /**
     * Updates the error labels with the currently selected agenda
     */
    private void changeErrorLabels() {
        if (Context.getInstance().getCurrentAgendaName() == null) {
            String message = "Please select or create an Agenda";
            completedTaskError.setText(message);
            upcomingTaskError.setText(message);
        } else {
            String message = Context.getInstance().getCurrentAgendaName();
            completedTaskError.setText(message);
            upcomingTaskError.setText(message);
        }
    }


    /**
     * Helper method. Enables column values to be edited for the upcoming task table.
     * Updates the database with the Users Changes
     */
    private void setOnEditCommitHandlers()  {

            ucTaskCol.setCellFactory(TextFieldTableCell.forTableColumn());
            ucTaskCol.setOnEditCommit(event -> {

                event.getRowValue().setTaskTitle(event.getNewValue());

                db.updateTask("title", event.getNewValue(), event.getRowValue().getTaskID());



            });


            ucDifficultyCol.setCellFactory(TextFieldTableCell.forTableColumn());
            ucDifficultyCol.setOnEditCommit(e -> {

                db.updateTask("difficulty", e.getNewValue(), e.getRowValue().getTaskID());


                e.getRowValue().setDifficulty(e.getNewValue());

            });

            ucNotesCol.setCellFactory(TextFieldTableCell.forTableColumn());
            ucNotesCol.setOnEditCommit(e -> {

                db.updateTask("notes", e.getNewValue(), e.getRowValue().getTaskID());
                e.getRowValue().setNotes(e.getNewValue());

            });
            ucPriorityCol.setCellFactory(TextFieldTableCell.forTableColumn());
            ucPriorityCol.setOnEditCommit(e -> {

                db.updateTask("priority", e.getNewValue(), e.getRowValue().getTaskID());


                e.getRowValue().setPriority(e.getNewValue());
            });

    }


    /**
     * Resets fields in the create task tab
     */
    private void resetNewTaskFields() {
        newTaskDescription.setText("");
        newTaskNotes.setText("");
        newTaskDeadline.getEditor().clear();
        newTaskDifficulty.getSelectionModel().selectFirst();
        newTaskPriority.getSelectionModel().selectFirst();
    }

    /**
     * Populates the list of agendas for the currently logged in user
     * Called when the user logs in
     */
    private void populateAgendaList() {
        int currUserId = Context.getInstance().getCurrentUserID();
        System.out.println("Logged in user: " + currUserId);

        ArrayList<Agenda> agendas = (ArrayList<Agenda>) adb.getUserAgendas();


        //From this
        /*for (Agenda current : agendas) {
            addAgendaMenuItem(current);
         }*/

        //To this
        agendas.forEach(this::addAgendaMenuItem);


    }

    /**
     * Adds an Agenda to the Agendas drop down menu
     *
     * @param agenda to be added
     */
    private void addAgendaMenuItem(final Agenda agenda) {
        AgendaMenuItem menuItem = new AgendaMenuItem(agenda);

        //May seem confusing at first, a lambda essentially cleans up the syntax for anonymous inner classes
        menuItem.setOnAction(e -> {

            Agenda selected = menuItem.getMyAgenda();

            System.out.println("Selected menu item " + menuItem.getText());

            Context.getInstance().setCurrentAgendaID(selected.getAgendaID());
            Context.getInstance().setCurrentAgendaName(selected.getAgendaTitle());
            ObservableList<Task> obsUpcoming = db.getAgendaTasks(selected, false);
            ObservableList<Task> obsCompleted = db.getAgendaTasks(selected, true);
            upcomingTaskTable.setItems(obsUpcoming);
            completedTaskTable.setItems(obsCompleted);



            changeErrorLabels();

        });

        //Add mew agenda to button group and add it the menu.
        menuItem.setToggleGroup(agendaGroup);
        AgendasMenu.getItems().add(menuItem);
    }

    //------------------------------------------------------------------------------------------------------------------
    // Define behavior for UI elements
    //------------------------------------------------------------------------------------------------------------------


    /**
     * Defines the behavior for the create task button
     */
    @FXML
    private void getAndPushTaskDetails() {

        if (Context.getInstance().getCurrentAgendaName() != null) {
            String taskName = newTaskDescription.getText();


            int taskID = taskName.hashCode(); //hella sneaky sneaks

            LocalDate ld = newTaskDeadline.getValue();
            java.sql.Date timestamp = null;

            if (ld != null) timestamp = java.sql.Date.valueOf(ld);

            System.out.println(timestamp);


            int completed = 0;
            String difficulty = newTaskDifficulty.getSelectionModel().getSelectedItem().toString();
            String priority = newTaskPriority.getSelectionModel().getSelectedItem().toString();
            java.sql.Date timeCompleted = new java.sql.Date(System.currentTimeMillis());
            String notes = newTaskNotes.getText();
            int agendaID = Context.getInstance().getCurrentAgendaID();

            Task task = new Task(taskID, taskName, timestamp, completed, difficulty, priority, timeCompleted, notes, agendaID);

            if (db.taskExists(task)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Duplicate task found, try again", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            } else {
                db.addTask(task);
                upcomingTaskTable.getItems().add(task);
                resetNewTaskFields();
            }
        } else {
            String message = "Please create or select an Agenda before making a task.";
            Alert noAgendaAlert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
            noAgendaAlert.showAndWait().filter(response -> response == ButtonType.OK);
        }


    }



    /**
     * Callback method to handle behavior when the upcoming tasks
     * tab is selected.
     */
    @FXML
    private void upcomingTabSelected() {
        //TODO redistribute some of this behavior
        Agenda selected = null;
        if (Context.getInstance().getCurrentAgendaName() != null) {
            selected = adb.getAgendaByTitle();
        }

        if (selected != null) {

            System.out.println("Agenda obtained: " + selected.getAgendaTitle());

            Context.getInstance().setCurrentAgendaID(selected.getAgendaID());

            ObservableList<Task> obs = db.getAgendaTasks(selected, false); //false = upcoming tasks

            completedTaskTable.setItems(obs);
            taskList.addAll(obs);


        }

    }

    /**
     *  Define behavior of selecting the completed Tab.
     *  Populates list with upcoming tasks.
     */
    @FXML
    private void completedTabSelected() {

        Agenda selected = null;
        if (Context.getInstance().getCurrentAgendaName() != null) {
            selected = adb.getAgendaByTitle();
        }

        if (selected != null) {

            System.out.println("Agenda obtained: " + selected.getAgendaTitle());

            Context.getInstance().setCurrentAgendaID(selected.getAgendaID());

            ObservableList<Task> obs = db.getAgendaTasks(selected, true); //true = completed tasks

            completedTaskTable.setItems(obs);

            taskList.addAll(obs);

        }

    }


    /**
     * Defines the functionality for the delete Context Menu option
     */
    @FXML
    private void handleContextDelete() {
        Task selected = upcomingTaskTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            db.removeTask(selected);
            upcomingTaskTable.getItems().removeAll(upcomingTaskTable.getSelectionModel().getSelectedItem());
        } //TODO consider putting a dialog here too
    }

    /**
     * Defines the functionality for the mark complete Context Menu option
     */
    @FXML
    private void handleContextComplete() {
        Task selected = upcomingTaskTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            db.completeTask(selected);
            upcomingTaskTable.getItems().removeAll(upcomingTaskTable.getSelectionModel().getSelectedItems());
            completedTaskTable.getItems().add(selected);
        } else {
            String message = "Please select a task to complete.";
            Alert failedDeleteAlert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK) ;
            failedDeleteAlert.showAndWait().filter(response -> response == ButtonType.OK);
        }

    }

    /**
     * Defines the functionality for deleting from the completed task table
     */
    @FXML
    private void handleCompletedContextDelete() {
        Task selected = completedTaskTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            db.removeTask(selected);
            completedTaskTable.getItems().removeAll(completedTaskTable.getSelectionModel().getSelectedItem());
        } else {
            String message = "Please select a task to delete.";
            Alert failedDeleteAlert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK) ;
            failedDeleteAlert.showAndWait().filter(response -> response == ButtonType.OK);
        }
    }

    /**
     * Defines the behavior for the create agenda button
     */
    @FXML
    private void handleCreateAgenda() {
        String title = newAgendaName.getText();
        int ID = title.hashCode();
        int user = Context.getInstance().getCurrentUserID();
        Agenda agenda = new Agenda(ID, title, user);
        adb.createAgenda(agenda);
        addAgendaMenuItem(agenda);

        newAgendaName.setText("");
    }

    @FXML
    private void switchUser() {
        Context.getInstance().switchUser();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));

            Stage stage = (Stage) guiRoot.getScene().getWindow();
            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

            stage.setScene(scene);
            stage.setTitle("Please log in to Agendizer");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void exit() {
        Platform.exit();
    }






}
