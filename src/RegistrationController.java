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


    @FXML
    private void handleReturnToLogin(final MouseEvent event) throws IOException {
        SceneController.swapScene("view/login.fxml", "Please Log In To Agendizer", event, getClass());
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
        } else if (!passwordsMatch) {
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
        final CharSequence inputStr = theEmail;
        final Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
