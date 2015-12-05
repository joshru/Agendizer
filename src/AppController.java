import Databases.AgendaDB;
import Databases.TaskDB;
import Model.Agenda;
import Model.Context;
import Model.Task;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

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
    @FXML private Button upcomingNewTaskButton;
    @FXML private Button completedNewTaskButton;
    @FXML private TableView upcomingTaskTable;
    @FXML private TableView completedTaskTable;

    @FXML private ContextMenu taskRowMenu;
    @FXML private MenuItem cMenuCompleted;
    @FXML private MenuItem cMenuDelete;
    @FXML private Menu AgendasMenu;


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

    @FXML private TextField newTaskDescpriton;
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

        Agenda selected = adb.getAdendaByTitle(Context.getInstance().getCurrentAgendaName());
        Context.getInstance().setCurrentAgendaID(selected.getAgendaID());
        ObservableList<Task> obs = db.getAgendaTasks(selected);
        upcomingTaskTable.setItems(obs);

    }

    @FXML
    private void completedTabSelected() {
        ObservableList<Task> tasks = null;
        try {
            tasks = db.getCompletedTasks();
            completedTaskTable.setItems(tasks);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleContextDelete() {
        Task selected = (Task) upcomingTaskTable.getSelectionModel().getSelectedItem();
//        System.out.println(selected.toString());
        db.removeTask(selected);
        upcomingTaskTable.getItems().removeAll(upcomingTaskTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleContextComplete() {
        Task selected = (Task) upcomingTaskTable.getSelectionModel().getSelectedItem();
        try {
            db.completeTask(selected);
            upcomingTaskTable.getItems().removeAll(upcomingTaskTable.getSelectionModel().getSelectedItems());
        } catch (SQLException e) {
            e.printStackTrace();
        }

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


    //https://community.oracle.com/thread/2486012?tstart=0
    // helpful link about combo boxes
    @FXML
    private void getTaskDetails() {
        String taskName = newTaskDescpriton.getText();

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


    }

    private void resetNewTaskFields() {
        newTaskDescpriton.setText("");
        newTaskDeadline.getEditor().clear();
//        newTaskDifficulty.

    }


    private void populateAgendaList() {
        int currUserId = Context.getInstance().getCurrentUserID();
        System.out.println("Logged in user: " + currUserId);
        try {
            ArrayList<Agenda> agendas = (ArrayList<Agenda>) adb.getUserAgendas();

            for (Agenda current : agendas) {
                addAgendaMenuItem(current);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addAgendaMenuItem(final Agenda agenda) {
        RadioMenuItem menuItem = new RadioMenuItem(agenda.getAgendaTitle());

        //TODO populate list with shit
        menuItem.setOnAction(e -> { //hella hax
            try {
                Agenda selected = adb.getAdendaByTitle(menuItem.getText());
                Context.getInstance().setCurrentAgendaID(selected.getAgendaID());
                Context.getInstance().setCurrentAgendaName(agenda.getAgendaTitle());
                ObservableList<Task> obs = db.getAgendaTasks(selected);
                upcomingTaskTable.setItems(obs);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        menuItem.setToggleGroup(agendaGroup);
        AgendasMenu.getItems().add(menuItem);
    }

}
