package Controller;

import Databases.AgendaDB;
import Databases.TaskDB;
import Databases.UserDB;
import Model.Agenda;
import Model.Context;
import Model.Task;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller class to handle register Scene behavior
 * @author Josh Rueschenberg
 * @version 1339
 */
public class RegistrationController {
    /**
     * All fields with @FXML tags correspond with a GUI element defined in the /view/register.fxml file
     * Names for these fields are the same as the FX:ID tag assigned to them
    */
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameRegisterField;
    @FXML private TextField emailField;
    @FXML private TextField passwordRegisterField;
    @FXML private TextField passwordConfirmation;
    @FXML private Label registerWarningLabel;

    /**
     * References to databases
     */
    private final UserDB db = new UserDB();

    /**
     * Handles behavior for return to login button
     * @param event fired when button is pressed
     */
    @FXML
    private void handleReturnToLogin(final MouseEvent event) {
        SceneController.swapScene("/view/login.fxml", "Please Log In To Agendizer", event, getClass());
    }

    /**
     * Handles behavior when create user button is pressed
     * Validates user input and creates a user if successful
     * @param event fired when button is pressed
     */
    @FXML
    private void createNewUser(final MouseEvent event) {
        boolean isValid = validateRegistration();
        User newUser;
        if (isValid) {
            newUser = new User(usernameRegisterField.getText().hashCode(), usernameRegisterField.getText(), firstNameField.getText(),
                               lastNameField.getText(), emailField.getText(), passwordRegisterField.getText());
            boolean success = db.createUser(newUser);
            System.out.println("New user sent to the DB");


            if (success) {
                handleReturnToLogin(event);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "User " + usernameRegisterField.getText()
                + " Created successfully.", ButtonType.OK);
                successAlert.showAndWait().filter(response -> response == ButtonType.OK);

            }

        }
    }

    /**
     * Checks if user has given valid input before accepting registration
     * @return success flag
     */
    @FXML
    private boolean validateRegistration() {
        boolean isValid = false;
        boolean validEmail = emailFormatCheck(emailField.getText());
        boolean passwordsMatch = passwordRegisterField.getText().equals(passwordConfirmation.getText());

        registerWarningLabel.setText("");
        if (validEmail && passwordsMatch) {
            isValid = true;
            System.out.println("Registration info is valid");

        } else if (!validEmail) {


            //Super sexy
            Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid Email", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);

        } else {

            Alert pwAlert = new Alert(Alert.AlertType.WARNING, "Passwords do not match.", ButtonType.OK);
            pwAlert.showAndWait().filter(response -> response == ButtonType.OK);
        }
        return isValid;
    }

    /**
     * Verifies that the email has proper qualities of an email.
     * @param theEmail the email string to be tested.
     * @return the boolean indicating if the email was valid successful(true), or not(false).
     */
    private boolean emailFormatCheck(final String theEmail) {
        boolean isValid = false;
        final String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        final Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(theEmail);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
