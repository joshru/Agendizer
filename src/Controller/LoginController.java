package Controller;

import Databases.AgendaDB;
import Databases.UserDB;
import Model.Context;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Controller for defining behavior in the login screen
 * @author Josh Rueschenberg
 * @version 1338
 */
public class LoginController {
    /**
     * All fields with @FXML tags correspond with a GUI element defined in the /view/login.fxml file
     * Names for these fields are the same as the FX:ID tag assigned to them
     */
    @FXML private Label loginError;
    @FXML private TextField usernameLoginField;
    @FXML private PasswordField passwordLoginField;
    @FXML private Button loginButton;
    @FXML private Text newUserButton;
    @FXML private Button test;
    /**
     * References to Databases
     */
    private UserDB db = new UserDB();
    private AgendaDB adb = new AgendaDB();

    /**
     * Defines login button behavior
     * @param event that gets fired when the button is pressed
     * @throws IOException
     */
    @FXML
    private void handleLoginButton(MouseEvent event) throws IOException {
        System.out.println("button pressed"); //debug
        loginError.setText("");
        String user;
        String pass;
        if (usernameLoginField.getText() != null && passwordLoginField.getText() != null) {
            System.out.println("fields not null");
            user = usernameLoginField.getText();
            pass = passwordLoginField.getText();
            if (db.hasAccount(user, pass)) {
                System.out.println("matching account found");

                Context.getInstance().setCurrentUserID(user.hashCode());

                SceneController.swapScene("/view/gui.fxml", "Agendizer", event, getClass());

            } else {
                loginError.setText("Invalid Login Information.");
            }

        }

    }

    /**
     * Swaps to a register Scene when register button is pressed
     * @param event fired when button pressed
     * @throws IOException
     */
    @FXML
    private void handleRegisterButton(MouseEvent event) throws IOException {
        SceneController.swapScene("/view/register.fxml", "Agendizer Registration", event, getClass());
    }

    /**
     * Test method for creating agendas TODO delete me
     * @param event
     */
    @FXML
    private void testAgenda(MouseEvent event) {
//        Agenda a = new Agenda(1234, "test agenda", 1234556346);
//        adb.createAgenda(a);
    }

    /**
     * Super dirty debugging function TODO delete me
     * @param event
     * @throws IOException
     */
    @FXML
    private void bypassLogin(MouseEvent event) throws IOException {
        SceneController.swapScene("/view/gui.fxml", "Agendizer", event, getClass());
    }


}
