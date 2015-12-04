import Databases.TaskDB;
import Model.Task;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Josh Rueschenberg on 11/30/2015.
 */
public class AppController {

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


    // these columns should probably be in an array or something. messy
    @FXML private TableColumn<Task, String> ucTaskCol;
    @FXML private TableColumn<Task, String> ucDeadlineCol;
    @FXML private TableColumn<Task, String> ucDifficultyCol;
    @FXML private TableColumn<Task, String> ucUrgencyCol;
    @FXML private TableColumn<Task, String> ucPriorityCol;
    @FXML private TableColumn compTaskCol;
    @FXML private TableColumn compDeadlineCol;
    @FXML private TableColumn compDifficultyCol;
    @FXML private TableColumn compUrgencyCol;
    @FXML private TableColumn compPriorityCol;

    @FXML private TextField newTaskDescpriton;
    @FXML private DatePicker newTaskDeadline;
    @FXML private ChoiceBox newTaskDifficulty;
    @FXML private ChoiceBox newTaskUrgency;
    @FXML private ChoiceBox newTaskPriority;
    @FXML private Button newTaskButton;

    private TaskDB db = new TaskDB();

//    ObservableList<String> difficultyVals = FXCollections.observableArrayList("one", "two");

    @FXML
    private void initialize() {
        //when setting property values the string must MATCH the field name in the Task class
        ucTaskCol.setCellValueFactory(new PropertyValueFactory<Task, String>("taskTitle"));
        ucDeadlineCol.setCellValueFactory(new PropertyValueFactory<Task, String>("timeStamp"));
        ucDifficultyCol.setCellValueFactory(new PropertyValueFactory<Task, String>("difficulty"));
        ucUrgencyCol.setCellValueFactory(new PropertyValueFactory<Task, String>("urgency"));
        ucPriorityCol.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));
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
        List<Task> tasks = db.getTasks();
        System.out.println("Tasks grabbed = " +  tasks.size());
    }

    @FXML
    private void upcomingTabSelected() throws SQLException {
        ObservableList<Task> tasks = db.getTasks();
//        ObservableList<Task> data = FXCollections.observableArrayList();+
//        System.out.println("obs tasks found: " + tasks.size());
        upcomingTaskTable.setItems(tasks);
    }

    @FXML
    private void handleContextDelete() {
        Task selected = (Task) upcomingTaskTable.getSelectionModel().getSelectedItem();
//        System.out.println(selected.toString());
        db.removeTask(selected);
        upcomingTaskTable.getItems().removeAll(upcomingTaskTable.getSelectionModel().getSelectedItem());
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
        int agendaID = 666;

        Task task = new Task(taskID, taskName, timestamp, completed, difficulty,
                urgency, priority, timeCompleted, notes, location, agendaID);

        db.addTask(task);

//        System.out.println(taskID);
//        System.out.println(difficulty);
//        System.out.println(urgency);
//        System.out.println(priority);
    }

    private void resetNewTaskFields() {
        newTaskDescpriton.setText("");
        newTaskDeadline.getEditor().clear();
//        newTaskDifficulty.

    }


}
