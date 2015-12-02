import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Josh Rueschenberg on 11/30/2015.
 */
public class LoginController {

    @FXML private Label loginError;
    @FXML private TextField usernameLoginField;
    @FXML private PasswordField passwordLoginField;
    @FXML private Button loginButton;
    @FXML private Text newUserButton;



    @FXML
    private void handleLoginButton(MouseEvent event) throws IOException {
        SceneController.swapScene("view/gui.fxml", "Agendizer", event, getClass());
    }

    @FXML
    private void handleRegisterButton(MouseEvent event) throws IOException {
        SceneController.swapScene("view/register.fxml", "Agendizer Registration", event, getClass());
    }

}
