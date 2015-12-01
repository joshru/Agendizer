import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

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


        Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));
//        login.get
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

    public void switchToGUI() throws Exception {
        if (DEBUG) System.out.println("login button pressed");
        replaceSceneContent("gui.fxml");
//
//        Parent gui = FXMLLoader.load(getClass().getResource("gui.fxml"));
//        myPrimaryStage.setTitle("Agendizer");
//        myPrimaryStage.setScene(new Scene(gui, 1000, 700));
//        primaryStage.show();
    }

    public void switchToLogin() throws Exception {
        replaceSceneContent("login.fxml");
    }

    @FXML
    public Parent replaceSceneContent(String fxml) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//        Parent root = loader.load();
//        Scene scene = new Scene(root, 1000, 700);
//        myPrimaryStage.setScene(scene);
        Parent page = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = myPrimaryStage.getScene();
        if (scene == null) {
            scene = new Scene(page, 1000, 700);
            myPrimaryStage.setScene(scene);
            myPrimaryStage.show();
        } else {
            myPrimaryStage.getScene().setRoot(page);
        }
        myPrimaryStage.sizeToScene();
        return page;
    }



}
