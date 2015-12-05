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
 * Created by Josh on 12/1/2015.
 */
public class SceneController {

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
