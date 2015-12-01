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
public class LoginController implements Initializable {

    @FXML private Label loginError;
    @FXML private TextField usernameLoginField;
    @FXML private PasswordField passwordLoginField;
    @FXML private Button loginButton;
    @FXML private Text newUserButton;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameRegisterField;
    @FXML private TextField emailField;
    @FXML private TextField passwordRegisterField;
    @FXML private TextField passwordConfirmation;
    @FXML private Button createAccountButton;
    @FXML private Button returnToLoginButton;
    @FXML private Label registerWarningLabel;



    @FXML
    private void handleLoginButton(MouseEvent event) throws IOException {
        swapScene("view/gui.fxml", "Agendizer", event);
    }

    @FXML
    private void handleRegisterButton(MouseEvent event) throws IOException {
        swapScene("view/register.fxml", "Agendizer Registration", event);
    }

    @FXML
    private void handleReturnToLogin(MouseEvent event) throws IOException {
        swapScene("view/login.fxml", "Please Log In To Agendizer", event);
    }

    @FXML
    private void swapScene(final String fxml, final String title, MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root, 1000, 700);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
