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

    private ToggleGroup agendaGroup;

    private static TaskDB db = new TaskDB();
    private static AgendaDB adb = new AgendaDB();

//    ObservableList<String> difficultyVals = FXCollections.observableArrayList("one", "two");

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

    @FXML
    private void newTaskTabSetup() {
//        newTaskDifficulty.setItems(difficultyVals);
    }

    @FXML
    private void test() {
        System.out.println("button did a thing");
    }

    @FXML
    private void testDB() throws SQLException {
        List<Task> tasks = db.getUpcomingTasks();
        System.out.println("Tasks grabbed = " +  tasks.size());
    }

    @FXML
    private void upcomingTabSelected() throws SQLException {
//        ObservableList<Task> tasks = db.getUpcomingTasks();
//        upcomingTaskTable.setItems(tasks);
//        ObservableList<Task> data = FXCollections.observableArrayList();+
//        System.out.println("obs tasks found: " + tasks.size());

//        Agenda selected = adb.getAgendaByTitle(Context.getInstance().getCurrentAgendaName());
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

    @FXML
    private void completedTabSelected() throws SQLException {
//        ObservableList<Task> tasks;
//        try {
//            tasks = db.getCompletedTasks();
//            completedTaskTable.setItems(tasks);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        Agenda selected = null;
        if (Context.getInstance().getCurrentAgendaName() != null) {
            selected = adb.getAgendaByTitle();
        }

        if (selected != null) {


            System.out.println("Agenda obtained: " + selected.getAgendaTitle());

            Context.getInstance().setCurrentAgendaID(selected.getAgendaID());

            ObservableList<Task> obs = db.getAgendaTasks(selected, true); //true = completed tasks


            //Lambda warning: Don't look directly into the loop.
            obs.forEach(e -> {
                if (e.getCompleted() == 0) {
                    completedTaskTable.getItems().add(e);
                } else {
                    upcomingTaskTable.getItems().add(e);
                }
            });

            // upcomingTaskTable.setItems(obs);

        }

    }


    private void setOnEditCommitHandlers()  {

            ucTaskCol.setCellFactory(TextFieldTableCell.forTableColumn());
            ucTaskCol.setOnEditCommit(event -> {
//                ((Task) event.getTableView().getItems().get
//                        (event.getTablePosition().getRow()).setTaskTitle(
//                        event.getNewValue()));
                try {
                    db.updateTask("title", event.getNewValue());
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            });



            ucDeadlineCol.setOnEditCommit(e -> {
                try {
                    db.updateTask("timestamp", e.getNewValue());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
            ucDifficultyCol.setOnEditCommit(e -> {
                try {
                    db.updateTask("difficulty", e.getNewValue());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
            ucUrgencyCol.setOnEditCommit(e -> {
                try {
                    db.updateTask("urgency", e.getNewValue());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
            ucPriorityCol.setOnEditCommit(e -> {
                try {
                    db.updateTask("priority", e.getNewValue());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });

    }

    @FXML
    private void handleContextDelete() {
        Task selected = upcomingTaskTable.getSelectionModel().getSelectedItem();
//        System.out.println(selected.toString());
        db.removeTask(selected);
        upcomingTaskTable.getItems().removeAll(upcomingTaskTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleContextComplete() {
        Task selected = upcomingTaskTable.getSelectionModel().getSelectedItem();
        try {
            db.completeTask(selected);
            upcomingTaskTable.getItems().removeAll(upcomingTaskTable.getSelectionModel().getSelectedItems());
            completedTaskTable.getItems().add(selected);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleCompletedContextDelete() {
        Task selected = completedTaskTable.getSelectionModel().getSelectedItem();
//        System.out.println(selected.toString());
        db.removeTask(selected);
        completedTaskTable.getItems().removeAll(completedTaskTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleCreateAgenda() {
        String title = newAgendaName.getText();
        int ID = title.hashCode();
        int user = Context.getInstance().getCurrentUserID();
        Agenda agenda = new Agenda(ID, title, user);
        adb.createAgenda(agenda);
        addAgendaMenuItem(agenda);
    }


    @FXML
    private void getTaskDetails() {
        String taskName = newTaskDescription.getText();

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


    }

    private void resetNewTaskFields() {
        newTaskDescription.setText("");
        newTaskDeadline.getEditor().clear();
//        newTaskDifficulty.

    }


    private void populateAgendaList() {
        int currUserId = Context.getInstance().getCurrentUserID();
        System.out.println("Logged in user: " + currUserId);
        try {
            ArrayList<Agenda> agendas = (ArrayList<Agenda>) adb.getUserAgendas();


            //From this
            for (Agenda current : agendas) {
                addAgendaMenuItem(current);
            }
            //To this
           // agendas.forEach(element -> addAgendaMenuItem(element));


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addAgendaMenuItem(final Agenda agenda) {
        AgendaMenuItem menuItem = new AgendaMenuItem(agenda);
        menuItem.setOnAction(e -> {
            try {
              //  Agenda selected = adb.getAgendaByTitle(menuItem.getText()); //TODO think over this decision to extend RadioMenuItem
                Agenda selected = menuItem.getMyAgenda();

                System.out.println("Selected menu item " + menuItem.getText());

                Context.getInstance().setCurrentAgendaID(selected.getAgendaID());
                Context.getInstance().setCurrentAgendaName(selected.getAgendaTitle());
                ObservableList<Task> obsUpcoming = db.getAgendaTasks(selected, false);
                ObservableList<Task> obsCompleted = db.getAgendaTasks(selected, true);
                upcomingTaskTable.setItems(obsUpcoming);
                completedTaskTable.setItems(obsCompleted);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        menuItem.setToggleGroup(agendaGroup);
        AgendasMenu.getItems().add(menuItem);
    }

}
