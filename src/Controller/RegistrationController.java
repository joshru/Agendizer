package Controller;

import Databases.UserDB;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Josh on 12/1/2015.
 */
public class RegistrationController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameRegisterField;
    @FXML private TextField emailField;
    @FXML private TextField passwordRegisterField;
    @FXML private TextField passwordConfirmation;
    @FXML private Button createAccountButton;
    @FXML private Button returnToLoginButton;
    @FXML private Label registerWarningLabel;

    private UserDB db = new UserDB();


    @FXML
    private void handleReturnToLogin(final MouseEvent event) throws IOException {
        SceneController.swapScene("view/login.fxml", "Please Log In To Agendizer", event, getClass());
    }

    @FXML
    private void createNewUser(final MouseEvent event) {
        boolean isValid = validateRegistration();
        User newUser;
        if (isValid) {
            newUser = new User(usernameRegisterField.getText().hashCode(), usernameRegisterField.getText(), firstNameField.getText(),
                               lastNameField.getText(), emailField.getText(), passwordRegisterField.getText());
            boolean success = db.createUser(newUser);
            System.out.println("New user sent to the DB");

            try {
                if (success) {

                    handleReturnToLogin(event);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
            registerWarningLabel.setText("Invalid Email.");
        } else {
            registerWarningLabel.setText("Passwords do not match.");
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
