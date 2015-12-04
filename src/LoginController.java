import Databases.AgendaDB;
import Databases.UserDB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Created by Josh Rueschenberg on 11/30/2015.
 */
public class LoginController {

    @FXML private Label loginError;
    @FXML private TextField usernameLoginField;
    @FXML private PasswordField passwordLoginField;
    @FXML private Button loginButton;
    @FXML private Text newUserButton;
    @FXML private Button test;

    private UserDB db = new UserDB();
    private AgendaDB adb = new AgendaDB();


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

            } else {
                loginError.setText("Invalid Login Information.");
            }

        }

    }

    @FXML
    private void handleRegisterButton(MouseEvent event) throws IOException {
        SceneController.swapScene("view/register.fxml", "Agendizer Registration", event, getClass());
    }

    @FXML
    private void testAgenda(MouseEvent event) {
//        Agenda a = new Agenda(1234, "test agenda", 1234556346);
//        adb.createAgenda(a);
    }

    @FXML
    private void bypassLogin(MouseEvent event) throws IOException {
        SceneController.swapScene("view/gui.fxml", "Agendizer", event, getClass());
    }


}
