package Controller;

import Databases.AgendaDB;
import Databases.TaskDB;
import Model.Agenda;
import Model.Context;
import Model.Task;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import view.AgendaMenuItem;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Josh Rueschenberg on 11/30/2015.
 */
public class AppController implements Initializable {

    /**
     *
     * All fields with @FXML tag are references to GUI elements defined in
     * the FXML files located in the /view package.
     *
     */
    //TODO remove unused references to GUI elements

    @FXML private ImageView appLogo;
    @FXML private Button searchButton;
    @FXML private TextField searchField;
    @FXML private TabPane tabContainer;
    @FXML private Tab upcomingTab;
    @FXML private Tab completedTab;
    @FXML private Tab newTaskTab;
    @FXML private Label upcomingTaskError;
    @FXML private Label completedTaskError;
    @FXML private TableView<Task> upcomingTaskTable;
    @FXML private TableView<Task> completedTaskTable;

    @FXML private ContextMenu taskRowMenu;
    @FXML private MenuItem cMenuCompleted;
    @FXML private MenuItem cMenuDelete;
    @FXML private Menu AgendasMenu;


    @FXML private ContextMenu completedContextMenu;
    @FXML private MenuItem cCompleteDelete;

    // these columns should probably be in an array or something. messy
    @FXML private TableColumn<Task, String> ucTaskCol;
    @FXML private TableColumn<Task, String> ucDeadlineCol;
    @FXML private TableColumn<Task, String> ucDifficultyCol;
    @FXML private TableColumn<Task, String> ucUrgencyCol;
    @FXML private TableColumn<Task, String> ucPriorityCol;
    @FXML private TableColumn<Task, String> compTaskCol;
    @FXML private TableColumn<Task, String> compDeadlineCol;
    @FXML private TableColumn<Task, String> compDifficultyCol;
    @FXML private TableColumn<Task, String> compUrgencyCol;
    @FXML private TableColumn<Task, String> compPriorityCol;

    @FXML private TextField newTaskDescription;
    @FXML private DatePicker newTaskDeadline;
    @FXML private ChoiceBox newTaskDifficulty;
    @FXML private ChoiceBox newTaskUrgency;
    @FXML private ChoiceBox newTaskPriority;
    @FXML private Button newTaskButton;

    @FXML private TextField newAgendaName;
    @FXML private Button newAgendaButton;

    /**Group for toggle buttons in the adenda drop down menu*/
    private ToggleGroup agendaGroup;
    /** Reference to task DB */
    private static TaskDB db = new TaskDB();
    /** Reference to Agenda database*/
    private static AgendaDB adb = new AgendaDB();

//    ObservableList<String> difficultyVals = FXCollections.observableArrayList("one", "two");

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
        ucDifficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        ucUrgencyCol.setCellValueFactory(new PropertyValueFactory<>("urgency"));
        ucPriorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));

        compTaskCol.setCellValueFactory(new PropertyValueFactory<>("taskTitle"));
        compDeadlineCol.setCellValueFactory(new PropertyValueFactory<>("timeCompleted"));
        compDifficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        compUrgencyCol.setCellValueFactory(new PropertyValueFactory<>("urgency"));
        compPriorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));

        agendaGroup = new ToggleGroup();

        upcomingTaskTable.setPlaceholder(new Label("Please select an agenda or create a new task."));
        completedTaskTable.setPlaceholder(new Label("Please select an agenda."));
        setOnEditCommitHandlers();
        populateAgendaList();
    }




    /**
     * Helper method. Enables column values to be edited for the upcoming task table.
     * Updates the database with the Users Changes
     */
    private void setOnEditCommitHandlers()  {

            ucTaskCol.setCellFactory(TextFieldTableCell.forTableColumn());
            ucTaskCol.setOnEditCommit(event -> {
//                ((Task) event.getTableView().getItems().get
//                        (event.getTablePosition().getRow()).setTaskTitle(
//                        event.getNewValue()));
                event.getRowValue().setTaskTitle(event.getNewValue());

                db.updateTask("title", event.getNewValue(), event.getRowValue().getTaskID());



            });

            //TODO find out how to update date column
            /*ucDeadlineCol.setCellFactory(TextFieldTableCell.forTableColumn());
            ucDeadlineCol.setOnEditCommit(e -> {



                try {
                    db.updateTask("timestamp", e.getNewValue(), e.getRowValue().getTaskID());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                //TODO add invalid input checking
                e.getRowValue().setTimeStamp(java.sql.Date.valueOf(e.getNewValue()));
            });*/


            ucDifficultyCol.setCellFactory(TextFieldTableCell.forTableColumn());
            ucDifficultyCol.setOnEditCommit(e -> {

                db.updateTask("difficulty", e.getNewValue(), e.getRowValue().getTaskID());


                e.getRowValue().setDifficulty(e.getNewValue());

            });

            ucUrgencyCol.setCellFactory(TextFieldTableCell.forTableColumn());
            ucUrgencyCol.setOnEditCommit(e -> {

                db.updateTask("urgency", e.getNewValue(), e.getRowValue().getTaskID());
                e.getRowValue().setUrgency(e.getNewValue());

            });
            ucPriorityCol.setCellFactory(TextFieldTableCell.forTableColumn());
            ucPriorityCol.setOnEditCommit(e -> {

                db.updateTask("priority", e.getNewValue(), e.getRowValue().getTaskID());


                e.getRowValue().setPriority(e.getNewValue());
            });

    }



    /**
     * Resets fields in the create task tab
     * TODO complete me
     */
    private void resetNewTaskFields() {
        newTaskDescription.setText("");
        newTaskDeadline.getEditor().clear();
        newTaskDifficulty.getSelectionModel().selectFirst();
        newTaskPriority.getSelectionModel().selectFirst();
        newTaskUrgency.getSelectionModel().selectFirst();
//        newTaskDifficulty.

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
    public void addAgendaMenuItem(final Agenda agenda) {
        AgendaMenuItem menuItem = new AgendaMenuItem(agenda);

        //May seem confusing at first, a lambda essentially cleans up the syntax for anonymous inner classes
        menuItem.setOnAction(e -> {

              //  Agenda selected = adb.getAgendaByTitle(menuItem.getText()); //TODO think over this decision to extend RadioMenuItem
            Agenda selected = menuItem.getMyAgenda();

            System.out.println("Selected menu item " + menuItem.getText());

            Context.getInstance().setCurrentAgendaID(selected.getAgendaID());
            Context.getInstance().setCurrentAgendaName(selected.getAgendaTitle());
            ObservableList<Task> obsUpcoming = db.getAgendaTasks(selected, false);
            ObservableList<Task> obsCompleted = db.getAgendaTasks(selected, true);
            upcomingTaskTable.setItems(obsUpcoming);
            completedTaskTable.setItems(obsCompleted);

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
     * TODO rename me to make it clear a task is being created
     */
    @FXML
    private void getTaskDetails() {
        String taskName = newTaskDescription.getText();


        //TODO change primary keys to INTEGER AUTOINCREMENT
        int taskID = taskName.hashCode(); //hella sneaky sneaks

        LocalDate ld = newTaskDeadline.getValue();
        java.sql.Date timestamp = null;

        if (ld != null) timestamp = java.sql.Date.valueOf(ld);

        System.out.println(timestamp);


        int completed = 0;
        String difficulty = newTaskDifficulty.getSelectionModel().getSelectedItem().toString();
        String urgency = newTaskUrgency.getSelectionModel().getSelectedItem().toString();
        String priority = newTaskPriority.getSelectionModel().getSelectedItem().toString();
        java.sql.Date timeCompleted = new java.sql.Date(System.currentTimeMillis());
        String notes = "test notes";
        String location = "ur mum's house";
        int agendaID = Context.getInstance().getCurrentAgendaID();

        Task task = new Task(taskID, taskName, timestamp, completed, difficulty,
                urgency, priority, timeCompleted, notes, location, agendaID);

        db.addTask(task);
        upcomingTaskTable.getItems().add(task);
        resetNewTaskFields();


    }


    /**
     * Test method for GUI elements TODO delete me
     */
    @FXML
    private void test() {
        System.out.println("button did a thing");
    }

    /**
     * Test function for DB TODO delete me before turning in

     */
    @FXML
    private void testDB() {
        List<Task> tasks = db.getUpcomingTasks();
        System.out.println("Tasks grabbed = " +  tasks.size());
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


            //Lambda warning: Don't look directly into the loop.
            obs.forEach(e -> {
                if (e.getCompleted() == 0) {
                    completedTaskTable.getItems().add(e);
                } else {
                    upcomingTaskTable.getItems().add(e);
                }
            });


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


            //Lambda warning: Don't look directly into the loop; you may be absorbed by its elegance
            obs.forEach(e -> {
                if (e.getCompleted() == 0) {
                    completedTaskTable.getItems().add(e);
                } else {
                    upcomingTaskTable.getItems().add(e);
                }
            });



        }

    }


    /**
     * Defines the functionality for the delete Context Menu option
     */
    @FXML
    private void handleContextDelete() {
        Task selected = upcomingTaskTable.getSelectionModel().getSelectedItem();

        db.removeTask(selected);
        upcomingTaskTable.getItems().removeAll(upcomingTaskTable.getSelectionModel().getSelectedItem());
    }

    /**
     * Defines the functionality for the mark complete Context Menu option
     */
    @FXML
    private void handleContextComplete() {
        Task selected = upcomingTaskTable.getSelectionModel().getSelectedItem();

        db.completeTask(selected);
        upcomingTaskTable.getItems().removeAll(upcomingTaskTable.getSelectionModel().getSelectedItems());
        completedTaskTable.getItems().add(selected);


    }

    /**
     * Defines the functionality for deleting from the completed task table
     */
    @FXML
    private void handleCompletedContextDelete() {
        Task selected = completedTaskTable.getSelectionModel().getSelectedItem();

        db.removeTask(selected);
        completedTaskTable.getItems().removeAll(completedTaskTable.getSelectionModel().getSelectedItem());
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






}
