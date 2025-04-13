package pizza_shop_system.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pizza_shop_system.account.SignUpHandler;

import java.io.IOException;
import java.util.ArrayList;

public class SignUpController extends BaseController {
    private String submittedEmail;
    private String submittedPassword;
    private String submittedVerifyPassword;
    private String submittedName;
    private String submittedAddress;
    private String submittedPhoneNumber;
    private final SignUpHandler signUpHandler = new SignUpHandler();
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField verifyPasswordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;

    @FXML
    public void handleEmailChanged() {
        this.submittedEmail = emailField.getText();
    }
    @FXML
    public void handlePasswordChanged() {
        this.submittedPassword = passwordField.getText();
    }
    @FXML
    public void handleVerifyPasswordChanged() {
        this.submittedVerifyPassword = verifyPasswordField.getText();
    }
    @FXML
    public void handleNameChanged() {
        this.submittedName = nameField.getText();
    }
    @FXML
    public void handleAddressChanged() {
        this.submittedAddress = addressField.getText();
    }
    @FXML
    public void handlePhoneNumberChanged() {
        this.submittedPhoneNumber = phoneNumberField.getText();
    }

    public void handleSignUpClick() throws IOException {
        ArrayList<String> invalidConditions = signUpHandler.AttemptSignUp(submittedEmail, submittedPassword, submittedVerifyPassword, submittedName, submittedAddress, submittedPhoneNumber);
        if (invalidConditions == null) {
            // Sign up successful
            System.out.println("Signed up!");
        } else {
            // Implement display sign up warnings to user
            System.out.println(invalidConditions);
        }
    }

    public void handleBackToLoginClick() {
        switchScene("Login");
    }
}
