import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

//mysql username: _445team2
//mysql password: poddoif

public class Main extends Application {

    private static boolean DEBUG = true;

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

    @FXML private Button loginButton;

    private Stage myPrimaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent gui = FXMLLoader.load(getClass().getResource("gui.fxml"));

        myPrimaryStage = primaryStage;

        Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));
//        login.get
        myPrimaryStage.setTitle("Please Log In To Agendizer");
        myPrimaryStage.setScene(new Scene(login, 1000, 700));
        myPrimaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void test() {
        System.out.println("HEY MY BUTTON DID A THING! NEAT!!");
    }

    public void switchToGUI() throws Exception {
        if (DEBUG) System.out.println("login button pressed");

        Parent gui = FXMLLoader.load(getClass().getResource("gui.fxml"));
//        myPrimaryStage.setTitle("Agendizer");
        myPrimaryStage.setScene(new Scene(gui, 1000, 700));
//        primaryStage.show();
    }

}
