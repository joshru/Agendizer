import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//mysql username: _445team2
//mysql password: poddoif

public class Main extends Application {

    private static boolean DEBUG = true;



    private Stage myPrimaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent gui = FXMLLoader.load(getClass().getResource("gui.fxml"));

        myPrimaryStage = primaryStage;
//        switchToLogin();
//        primaryStage.show();


        Parent login = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Please Log In To Agendizer");
        primaryStage.setScene(new Scene(login, 1000, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void test() {
        System.out.println("HEY MY BUTTON DID A THING! NEAT!!");
    }




}
