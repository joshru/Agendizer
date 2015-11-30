import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javax.swing.*;
//import javax.swing.text.html.ImageView;

public class Controller {

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

    // these columns should probably be in an array or something. messy
    @FXML private TableColumn ucTaskCol;
    @FXML private TableColumn ucDeadlineCol;
    @FXML private TableColumn ucDifficultyCol;
    @FXML private TableColumn ucUrgencyCol;
    @FXML private TableColumn ucPriorityCol;
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


    public void test() {
        System.out.println("HEY MY BUTTON DID A THING! NEAT!!");
    }

}
