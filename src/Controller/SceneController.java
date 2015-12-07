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
class SceneController {
    /**
     * Replaces the current scene
     * @param fxml defining path to new fxml file
     * @param title of the new scene to be displayed
     * @param event source of where the request for new scene came from
     * @param context context of where to put the new scene
     */
    @FXML
    public static void swapScene(final String fxml, final String title, MouseEvent event, Class context) {
        Parent root;
        try {
            root = FXMLLoader.load(context.getResource(fxml));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, stage.getWidth() , stage.getHeight());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
