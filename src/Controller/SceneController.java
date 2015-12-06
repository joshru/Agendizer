package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Helper class to handle swapping scenes.
 * @author Josh Rueschenberg
 * @version 1340
 */
public class SceneController {
    /**
     * Replaces the current scene
     * TODO Josh verify javadoc param definitions here
     * @param fxml defining ui elements
     * @param title of the scene
     * @param event being fired
     * @param context to place scene on
     * @throws IOException
     */
    @FXML
    public static void swapScene(final String fxml, final String title, MouseEvent event, Class context) throws IOException {
        Parent root = FXMLLoader.load(context.getResource(fxml));
        Scene scene = new Scene(root, 1000, 700);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

}
