import Databases.UserDB;
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

    private UserDB db = new UserDB();


    @FXML
    private void handleLoginButton(MouseEvent event) throws IOException {
        System.out.println("button pressed");
        loginError.setText("");
        String user;
        String pass;
        if (usernameLoginField.getText() != null && passwordLoginField.getText() != null) {
            System.out.println("fields not null");
            user = usernameLoginField.getText();
            pass = passwordLoginField.getText();
            if (db.hasAccount(user, pass)) {
                System.out.println("matching account found");
                SceneController.swapScene("view/gui.fxml", "Agendizer", event, getClass());
            }

        } else if (usernameLoginField.getText() == null) {
            loginError.setText("Invalid username");
        } else {
            loginError.setText("Invalid password");
        }

    }

    @FXML
    private void handleRegisterButton(MouseEvent event) throws IOException {
        SceneController.swapScene("view/register.fxml", "Agendizer Registration", event, getClass());
    }

}
